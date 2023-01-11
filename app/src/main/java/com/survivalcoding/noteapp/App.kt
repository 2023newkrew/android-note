package com.survivalcoding.noteapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.survivalcoding.noteapp.Config.Companion.TABLE_NAME
import com.survivalcoding.noteapp.data.data_source.database.NoteDatabase
import com.survivalcoding.noteapp.data.repository.NoteRepositoryImpl
import com.survivalcoding.noteapp.domain.use_case.*

class App : Application() {
    val noteDatabase by lazy {
        Room.databaseBuilder(
            ApplicationProvider.getApplicationContext() as Context,
            NoteDatabase::class.java,
            TABLE_NAME
        ).build()
    }

    val noteDao by lazy {
        noteDatabase.noteDao()
    }

    val noteUseCaseBundle by lazy {
        val noteRepositoryImpl = NoteRepositoryImpl(noteDao)

        NoteUseCaseBundle(
            GetNotesUseCase(noteRepositoryImpl),
            InsertNoteUseCase(noteRepositoryImpl),
            DeleteNoteUseCase(noteRepositoryImpl),
            UpdateNoteUseCase(noteRepositoryImpl)
        )
    }
}