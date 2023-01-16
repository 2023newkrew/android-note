package com.survivalcoding.noteapp.data.repository

import androidx.annotation.WorkerThread
import com.survivalcoding.noteapp.data.data_source.dao.NoteDao
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    @WorkerThread
    override fun getNotes(orderKey: String): Flow<List<Note>> {
        return noteDao.getNotes(orderKey)
    }

    @WorkerThread
    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    @WorkerThread
    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }

    @WorkerThread
    override suspend fun updateNote(note: Note) {
        return noteDao.updateNote(note)
    }
}