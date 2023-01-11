package com.survivalcoding.noteapp.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.survivalcoding.noteapp.data.data_source.dao.NoteDao
import com.survivalcoding.noteapp.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}