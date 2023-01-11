package com.survivalcoding.noteapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.Config.Companion.FRAGMENT_CODE_EDIT
import com.survivalcoding.noteapp.Config.Companion.FRAGMENT_CODE_MAIN
import com.survivalcoding.noteapp.domain.use_case.NoteUseCaseBundle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun changeFragmentCode() {
        _state.value = state.value.copy(
            fragmentCode =
            if (state.value.fragmentCode == FRAGMENT_CODE_MAIN)
                FRAGMENT_CODE_EDIT
            else
                FRAGMENT_CODE_MAIN
        )

    }
}

data class MainState(
    val fragmentCode: Int = FRAGMENT_CODE_MAIN
)