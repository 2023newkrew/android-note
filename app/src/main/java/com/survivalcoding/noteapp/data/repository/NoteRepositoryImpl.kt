package com.survivalcoding.noteapp.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.survivalcoding.noteapp.Config.Companion.TABLE_NAME
import com.survivalcoding.noteapp.data.data_source.dao.NoteDao
import com.survivalcoding.noteapp.data.data_source.database.NoteDatabase
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
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