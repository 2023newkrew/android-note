package com.survivalcoding.noteapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.domain.use_case.NoteUseCaseBundle

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
}