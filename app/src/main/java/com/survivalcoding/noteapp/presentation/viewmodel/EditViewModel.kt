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

class EditViewModel(private val noteUseCaseBundle: NoteUseCaseBundle) : ViewModel() {
    companion object {
        val EditViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return EditViewModel((application as App).noteUseCaseBundle) as T
            }
        }
    }

    private val _state = MutableStateFlow(EditState())
    val state = _state.asStateFlow()

    fun updateNote(note: Note) = viewModelScope.launch { noteUseCaseBundle.updateNotesUseCase(note) }

    fun changeColor(colorCode: Int) {
        _state.value = state.value.copy(colorCode = colorCode)
    }
}

data class EditState(
    val colorCode: Int = 0
)