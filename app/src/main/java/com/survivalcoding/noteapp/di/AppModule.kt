package com.survivalcoding.noteapp.di

import android.content.Context
import androidx.room.Room
import com.survivalcoding.noteapp.Config
import com.survivalcoding.noteapp.data.data_source.dao.NoteDao
import com.survivalcoding.noteapp.data.data_source.database.NoteDatabase
import com.survivalcoding.noteapp.domain.repository.NoteRepository
import com.survivalcoding.noteapp.domain.use_case.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.use_case.GetNotesUseCase
import com.survivalcoding.noteapp.domain.use_case.InsertNoteUseCase
import com.survivalcoding.noteapp.domain.use_case.UpdateNoteUseCase
import com.survivalcoding.noteapp.domain.use_case.bundle.NoteUseCaseBundle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            Config.TABLE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteUseCaseBundle(noteRepository: NoteRepository): NoteUseCaseBundle {
        return NoteUseCaseBundle(
            GetNotesUseCase(noteRepository),
            InsertNoteUseCase(noteRepository),
            DeleteNoteUseCase(noteRepository),
            UpdateNoteUseCase(noteRepository)
        )
    }

}