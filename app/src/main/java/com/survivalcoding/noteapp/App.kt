package com.survivalcoding.noteapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.survivalcoding.noteapp.Config.Companion.TABLE_NAME
import com.survivalcoding.noteapp.data.data_source.database.NoteDatabase

class App : Application() {
    private val noteDatabase by lazy {
        Room.databaseBuilder(
            ApplicationProvider.getApplicationContext() as Context,
            NoteDatabase::class.java,
            TABLE_NAME
        ).build()
    }

    private val noteDao by lazy {
        noteDatabase.noteDao()
    }
}