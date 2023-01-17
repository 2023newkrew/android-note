package com.survivalcoding.noteapp.presentation.event

sealed class UserEvent {
    class ShowSnackBar(val message: String) : UserEvent()
}