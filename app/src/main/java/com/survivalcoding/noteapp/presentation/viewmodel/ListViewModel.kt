package com.survivalcoding.noteapp.presentation.viewmodel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.survivalcoding.noteapp.App
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
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.bundle.NoteUseCaseBundle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(
    private val application: Application,
    private val noteUseCaseBundle: NoteUseCaseBundle
) : ViewModel() {
    companion object {
        val ListViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ListViewModel(application, (application as App).noteUseCaseBundle) as T
            }
        }
    }

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    fun getNotes(orderKey: String = ORDER_KEY_TITLE_ASC): Flow<List<Note>> = noteUseCaseBundle.getNotesUseCase(orderKey)

    fun insertNode(note: Note) = viewModelScope.launch { noteUseCaseBundle.insertNoteUseCase(note) }

    fun deleteNote(note: Note) = viewModelScope.launch { noteUseCaseBundle.deleteNotesUseCase(note) }

    fun changeOrder(orderCode: Int, isReversed: Boolean) {
        val editor = application.getSharedPreferences(PREFS, MODE_PRIVATE).edit()
        editor.putString(PREFS_KEY_ORDER, orderKey)
        editor.apply()

        _state.value = state.value.copy(
            orderCode = orderCode,
            isReversed = isReversed,
            orderKey = orderKey
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

    fun convertOrderKeyToCode()
}

data class ListState(
    val orderCode: Int = ORDER_CODE_TITLE,
    val isReversed: Boolean = false,
    val orderKey: String = ORDER_KEY_TITLE_ASC
)