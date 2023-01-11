package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(orderKey: String): Flow<List<Note>> = repository.getNotes(orderKey)
}