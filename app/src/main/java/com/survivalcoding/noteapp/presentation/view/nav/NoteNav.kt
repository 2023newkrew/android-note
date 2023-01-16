package com.survivalcoding.noteapp.presentation.view.nav

sealed class NoteNav(val route: String) {
    object NoteList : NoteNav("note_list")
    object NoteAdd : NoteNav("note_add")
    object NoteUpdate : NoteNav("note_update")
}