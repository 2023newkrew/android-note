package com.survivalcoding.noteapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Query
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.Config.Companion.FRAGMENT_CODE_EDIT
import com.survivalcoding.noteapp.Config.Companion.FRAGMENT_CODE_MAIN
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TITLE_ASC
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.bundle.NoteUseCaseBundle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val noteUseCaseBundle: NoteUseCaseBundle) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainViewModel((application as App).noteUseCaseBundle) as T
            }
        }
    }

//    private val _state = MutableStateFlow(MainState(noteUseCaseBundle.getNotesUseCase()))
//    val state = _state.asStateFlow()

    fun getNotes(orderKey: String = ORDER_KEY_TITLE_ASC): Flow<List<Note>> = noteUseCaseBundle.getNotesUseCase(orderKey)

    fun addTest(){
        viewModelScope.launch {
            noteUseCaseBundle.insertNoteUseCase(Note(
                title = "noah",
                content = "good",
                colorCode = 2,
                time= 133223
            ))
        }

    }
    fun deleteNote(note:Note) {
        viewModelScope.launch {
            noteUseCaseBundle.deleteNotesUseCase(note)
        }
    }
}

data class MainState(
    val noteList: Flow<List<Note>>
)