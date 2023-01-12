package com.survivalcoding.noteapp.presentation.notes

import androidx.lifecycle.*
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases
import com.survivalcoding.noteapp.domain.util.NoteOrder
import com.survivalcoding.noteapp.domain.util.OrderType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val useCases: NoteUseCases,
) : ViewModel(), LifecycleEventObserver {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    private var recentlyDeletedNote: Note? = null

    var noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)

    fun onUserEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.Order -> {
                noteOrder = event.noteOrder
                getNotes()
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    useCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            NotesEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
        }
    }

    fun getNotes() {
        useCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = _state.value.copy(
                    notes = notes,
                )
            }
            .launchIn(viewModelScope)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            getNotes()
        }
    }
}