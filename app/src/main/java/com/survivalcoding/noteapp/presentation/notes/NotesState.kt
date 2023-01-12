package com.survivalcoding.noteapp.presentation.notes

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.util.NoteOrder
import com.survivalcoding.noteapp.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
)
