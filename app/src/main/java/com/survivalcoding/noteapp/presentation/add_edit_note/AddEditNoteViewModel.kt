package com.survivalcoding.noteapp.presentation.add_edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val repository: NoteRepository,
    private val useCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditNoteState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            viewModelScope.launch {
                repository.getNoteById(noteId)?.also { note ->
                    _state.value = state.value.copy(
                        noteId = note.id,
                        color = note.color,
                        title = note.title,
                        content = note.content,
                    )
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _state.value = state.value.copy(
                    color = event.color
                )
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch(
                    CoroutineExceptionHandler { _, throwable ->
                        viewModelScope.launch {
                            _event.emit(UiEvent.ShowSnackBar(throwable.message ?: "Unknown Error"))
                        }
                    }
                ) {
                    useCases.addNote(
                        Note(
                            title = event.title,
                            content = event.content,
                            timestamp = System.currentTimeMillis(),
                            color = state.value.color,
                            id = state.value.noteId,
                        )
                    )

                    _event.emit(UiEvent.SaveNote)
                }
            }
        }
    }
}

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
    object SaveNote : UiEvent()
}
