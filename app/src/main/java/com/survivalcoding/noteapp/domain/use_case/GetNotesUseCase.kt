package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRespository
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class GetNotesUseCase(
    private val repository: NoteRespository
) {
    suspend operator fun invoke(orderKey: String): Flow<List<Note>> {
            return try {
                repository.getNotes(orderKey)
            } catch (e: IOException) {
                throw IOException(e)
            }
    }
}