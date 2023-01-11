package com.survivalcoding.noteapp.domain.repository

import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(orderKey: String): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}