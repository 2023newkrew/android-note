package com.survivalcoding.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.use_case.bundle.NoteUseCaseBundle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val noteUseCaseBundle: NoteUseCaseBundle) :
    ViewModel() {

    private val _state = MutableStateFlow(AddState())
    val state = _state.asStateFlow()

    fun saveNote(note: Note) = viewModelScope.launch {
        noteUseCaseBundle.insertNoteUseCase(note)
    }

    fun changeColor(colorCode: Int) {
        _state.value = state.value.copy(colorCode = colorCode)
    }
}

data class AddState(
    val colorCode: Int = 0
)