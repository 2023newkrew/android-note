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
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class NoteRepositoryImplTest {

    private val noteDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        NoteDatabase::class.java
    ).build()
    private val noteDao = noteDatabase.noteDao()

    @Before
    fun setUp() = runBlocking {
        for (i in 0..4) {
            noteDao.insertNote(
                Note(
                    title = "$i Note",
                    content = "$i $i $i $i ",
                    time = System.currentTimeMillis(),
                    colorCode = i
                )
            )
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.getNotes(ORDER_KEY_TITLE_ASC)
                .collect {
                    assertEquals("0 Note", it[0].title)
                }
            noteDao.getNotes(ORDER_KEY_TITLE_DESC)
                .collect {
                    assertEquals("4 Note", it[0].title)
                }
            noteDao.getNotes(ORDER_KEY_COLOR_ASC)
                .collect {
                    assertTrue(it[0].colorCode < it[1].colorCode)
                }
            noteDao.getNotes(ORDER_KEY_COLOR_DESC)
                .collect {
                    assertTrue(it[0].colorCode > it[1].colorCode)
                }
            noteDao.getNotes(ORDER_KEY_TIME_ASC)
                .collect {
                    assertTrue(it[0].time < it[1].time)
                }
            noteDao.getNotes(ORDER_KEY_TIME_DESC)
                .collect {
                    assertTrue(it[0].time > it[1].time)
                }
        }
    }


    @Test
    fun insertNote() {
        CoroutineScope(Dispatchers.IO).launch {
            var count = 0
            noteDao.getNotes(ORDER_KEY_TITLE_ASC).collect {
                count = it.size
            }
            noteDao.insertNote(
                Note(
                    title = "5 Note",
                    content = "5 5 5 5 5 ",
                    time = System.currentTimeMillis(),
                    colorCode = 5
                )
            )
            noteDao.getNotes(ORDER_KEY_TITLE_ASC).collect {
                assertEquals(count + 1, it.size)
            }
        }
    }

    @Test
    fun deleteNote() {
        CoroutineScope(Dispatchers.IO).launch {
            var count = 0
            noteDao.getNotes(ORDER_KEY_TITLE_ASC).collect {
                count = it.size
                noteDao.deleteNote(it[0])
            }
            noteDao.getNotes(ORDER_KEY_TITLE_ASC).collect {
                assertEquals(count - 1, it.size)
            }
        }
    }

    @Test
    fun updateNote() {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.getNotes(ORDER_KEY_TITLE_ASC).collect {
                it[0].title = "Good"
                noteDao.updateNote(it[0])
            }
            noteDao.getNotes(ORDER_KEY_TITLE_ASC).collect {
                assertEquals("Good", it[0].title)
            }
        }
    }
}