package com.survivalcoding.noteapp

import android.app.Application
import androidx.room.Room
import com.survivalcoding.noteapp.data.data_source.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.use_case.AddNoteUseCase
import com.survivalcoding.noteapp.domain.use_case.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.use_case.GetNotesUseCase
import com.survivalcoding.noteapp.domain.use_case.NoteUseCases

class App : Application() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "dddd",
        ).build()
    }

    val repository: NoteRepository by lazy {
        NoteRepositoryImpl(db.noteDao)
    }

    val noteUseCases: NoteUseCases by lazy {
        NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
        )
    }
}