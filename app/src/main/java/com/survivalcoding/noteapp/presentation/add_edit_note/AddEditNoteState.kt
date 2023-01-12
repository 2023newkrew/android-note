package com.survivalcoding.noteapp.presentation.add_edit_note

import com.survivalcoding.noteapp.ui.colors

data class AddEditNoteState(
    val noteId: Int? = null,
    val color: Int = colors.first(),
    val title: String = "",
    val content: String = "",
)