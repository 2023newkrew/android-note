package com.survivalcoding.noteapp.presentation.viewmodel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.Config.Companion.ORDER_CODE_COLOR
import com.survivalcoding.noteapp.Config.Companion.ORDER_CODE_DATE
import com.survivalcoding.noteapp.Config.Companion.ORDER_CODE_TITLE
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_COLOR_ASC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_COLOR_DESC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TIME_ASC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TIME_DESC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TITLE_ASC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TITLE_DESC
import com.survivalcoding.noteapp.Config.Companion.PREFS
import com.survivalcoding.noteapp.Config.Companion.PREFS_KEY_ORDER
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.bundle.NoteUseCaseBundle
import com.survivalcoding.noteapp.presentation.event.UserEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val application: Application,
    private val noteUseCaseBundle: NoteUseCaseBundle
) : ViewModel() {


    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<UserEvent>()
    val event = _event.asSharedFlow()

    private var backupNote: Note? = null

    init {
        val prefs = application.getSharedPreferences(PREFS, MODE_PRIVATE)
        val orderKey = prefs.getString(PREFS_KEY_ORDER, ORDER_KEY_TITLE_ASC)
        val orderPair = convertOrderKeyToOrderPair(orderKey ?: ORDER_KEY_TITLE_ASC)
        _state.value = state.value.copy(
            orderCode = orderPair.first,
            isReversed = orderPair.second
        )
    }

    fun getNotes(orderKey: String = ORDER_KEY_TITLE_ASC): Flow<List<Note>> =
        noteUseCaseBundle.getNotesUseCase(orderKey)

    private fun insertNote(note: Note) =
        viewModelScope.launch {
            try {
                noteUseCaseBundle.insertNoteUseCase(note)
            } catch (exception: IOException) {
                _event.emit(
                    UserEvent.ShowSnackBar(
                        exception.message ?: application.getString(R.string.exception)
                    )
                )
            }
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            try {
                noteUseCaseBundle.deleteNotesUseCase(note)
                backupNote = note
                _event.emit(
                    UserEvent.ShowSnackBar(
                        application.getString(R.string.message_delete)
                    )
                )
            } catch (exception: IOException) {
                _event.emit(
                    UserEvent.ShowSnackBar(
                        exception.message ?: application.getString(R.string.exception)
                    )
                )
            }
        }

    fun restoreNote() = backupNote?.let { insertNote(backupNote as Note) }


    fun changeOrder(orderCode: Int, isReversed: Boolean) {
        val editor = application.getSharedPreferences(PREFS, MODE_PRIVATE).edit()
        editor.putString(PREFS_KEY_ORDER, convertOrderCodeToKey(orderCode, isReversed))
        editor.apply()

        _state.value = state.value.copy(
            orderCode = orderCode,
            isReversed = isReversed,
        )
    }

    fun convertOrderCodeToKey(orderCode: Int, isReversed: Boolean): String =
        if (isReversed) {
            when (orderCode) {
                ORDER_CODE_TITLE -> ORDER_KEY_TITLE_DESC
                ORDER_CODE_DATE -> ORDER_KEY_TIME_DESC
                ORDER_CODE_COLOR -> ORDER_KEY_COLOR_DESC
                else -> ORDER_KEY_TITLE_DESC
            }
        } else {
            when (orderCode) {
                ORDER_CODE_TITLE -> ORDER_KEY_TITLE_ASC
                ORDER_CODE_DATE -> ORDER_KEY_TIME_ASC
                ORDER_CODE_COLOR -> ORDER_KEY_COLOR_ASC
                else -> ORDER_KEY_TITLE_ASC
            }
        }

    private fun convertOrderKeyToOrderPair(orderKey: String): Pair<Int, Boolean> =
        when (orderKey) {
            ORDER_KEY_TITLE_ASC -> Pair(ORDER_CODE_TITLE, false)
            ORDER_KEY_TITLE_DESC -> Pair(ORDER_CODE_TITLE, true)
            ORDER_KEY_COLOR_ASC -> Pair(ORDER_CODE_COLOR, false)
            ORDER_KEY_COLOR_DESC -> Pair(ORDER_CODE_COLOR, true)
            ORDER_KEY_TIME_ASC -> Pair(ORDER_CODE_DATE, false)
            ORDER_KEY_TIME_DESC -> Pair(ORDER_CODE_DATE, true)
            else -> Pair(ORDER_CODE_TITLE, false)
        }
}

data class ListState(
    val orderCode: Int = ORDER_CODE_TITLE,
    val isReversed: Boolean = false,
)