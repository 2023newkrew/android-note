package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TITLE_ASC
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(orderKey: String = ORDER_KEY_TITLE_ASC): Flow<List<Note>> = repository.getNotes(orderKey)
}