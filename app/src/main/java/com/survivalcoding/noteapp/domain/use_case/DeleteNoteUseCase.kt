package com.survivalcoding.noteapp.domain.use_case

import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRespository
import java.io.IOException

class DeleteNoteUseCase(private val repository: NoteRespository) {

    suspend operator fun invoke(note: Note) = try {
        repository.deleteNote(note)
    } catch (e: IOException) {
        throw IOException(e)
    }
}