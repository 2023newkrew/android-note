package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.bundle.NoteUseCaseBundle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddViewModel(private val noteUseCaseBundle: NoteUseCaseBundle) : ViewModel() {
    companion object {
        val AddViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return AddViewModel((application as App).noteUseCaseBundle) as T
            }
        }
    }

    private val _state = MutableStateFlow(AddState())
    val state = _state.asStateFlow()

    fun saveNote(note: Note) = viewModelScope.launch { noteUseCaseBundle.insertNoteUseCase(note) }

    fun changeColor(colorCode: Int) {
        _state.value = state.value.copy(colorCode = colorCode)
    }
}

data class AddState(
    val colorCode: Int = 0
)