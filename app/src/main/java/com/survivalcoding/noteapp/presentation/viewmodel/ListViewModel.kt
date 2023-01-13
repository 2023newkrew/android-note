package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TITLE_ASC
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.bundle.NoteUseCaseBundle
import com.survivalcoding.noteapp.presentation.MainViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(private val noteUseCaseBundle: NoteUseCaseBundle) : ViewModel() {
    companion object {
        val ListViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ListViewModel((application as App).noteUseCaseBundle) as T
            }
        }
    }

    fun getNotes(orderKey: String = ORDER_KEY_TITLE_ASC): Flow<List<Note>> = noteUseCaseBundle.getNotesUseCase(orderKey)

    fun deleteNote(note:Note) {
        viewModelScope.launch {
            noteUseCaseBundle.deleteNotesUseCase(note)
        }
    }
}

