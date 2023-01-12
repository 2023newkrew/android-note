package com.survivalcoding.noteapp.presentation.notes

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
