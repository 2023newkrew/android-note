package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository

class InsertNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.insertNote(note)
}