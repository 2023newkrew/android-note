package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import java.io.IOException

class UpdateNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}