package com.survivalcoding.noteapp.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_COLOR_ASC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_COLOR_DESC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TIME_ASC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TIME_DESC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TITLE_ASC
import com.survivalcoding.noteapp.Config.Companion.ORDER_KEY_TITLE_DESC
import com.survivalcoding.noteapp.data.data_source.database.NoteDatabase
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteRepositoryImplTest {
    private val noteDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        NoteDatabase::class.java
    ).build()
    private val noteDao = noteDatabase.noteDao()

    @Before
    fun setUp() = runTest {
        for (i in 0..4) {
            noteDao.insertNote(
                Note(
                    title = "$i Note",
                    content = "$i $i $i $i ",
                    time = System.currentTimeMillis(),
                    colorCode = i
                )
            )
            delay(1000)
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getNotes() = runTest {
        var notes: List<Note> = noteDao.getNotes(ORDER_KEY_TITLE_ASC).first()
        assertEquals("0 Note", notes[0].title)

        notes = noteDao.getNotes(ORDER_KEY_TITLE_DESC).first()
        assertEquals("4 Note", notes[0].title)

        notes = noteDao.getNotes(ORDER_KEY_COLOR_ASC).first()
        assertTrue(notes[0].colorCode < notes[1].colorCode)

        notes = noteDao.getNotes(ORDER_KEY_COLOR_DESC).first()
        assertTrue(notes[0].colorCode > notes[1].colorCode)

        notes = noteDao.getNotes(ORDER_KEY_TIME_ASC).first()
        assertTrue(notes[0].time < notes[1].time)

        notes = noteDao.getNotes(ORDER_KEY_TIME_DESC).first()
        assertTrue(notes[0].time > notes[1].time)
    }

    @Test
    fun insertNote() = runTest {
        var notes: List<Note> = noteDao.getNotes(ORDER_KEY_TITLE_ASC).first()
        val prevSize = notes.size
        noteDao.insertNote(
            Note(
                title = "5 Note",
                content = "5 5 5 5 5 ",
                time = System.currentTimeMillis(),
                colorCode = 5
            )
        )
        notes = noteDao.getNotes(ORDER_KEY_TITLE_ASC).first()
        assertEquals(prevSize + 1, notes.size)
    }

    @Test
    fun deleteNote() = runTest {
        var notes: List<Note> = noteDao.getNotes(ORDER_KEY_TITLE_ASC).first()
        val prevSize = notes.size
        noteDao.deleteNote(notes[0])
        notes = noteDao.getNotes(ORDER_KEY_TITLE_ASC).first()
        assertEquals(prevSize - 1, notes.size)
    }

    @Test
    fun updateNote() = runTest {
        var notes: List<Note> = noteDao.getNotes(ORDER_KEY_TITLE_ASC).first()
        val firstNote = notes.first().copy(
            title = "Good"
        )
        val prevId = firstNote.id
        noteDao.updateNote(firstNote)
        notes = noteDao.getNotes(ORDER_KEY_TITLE_ASC).first()
        assertEquals("Good", notes.first { it.id == prevId }.title)
    }
}