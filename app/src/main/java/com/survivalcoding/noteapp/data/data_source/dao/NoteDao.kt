package com.survivalcoding.noteapp.data.dao

import androidx.room.*
import com.survivalcoding.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("""
       SELECT * FROM note ORDER BY
            CASE :orderKey WHEN 'title_asc' THEN title END ASC,
            CASE :orderKey WHEN 'title_desc' THEN title END DESC,
            CASE :orderKey WHEN 'color_asc' THEN colorCode END ASC,
            CASE :orderKey WHEN 'color_desc' THEN colorCode END DESC,
            CASE :orderKey WHEN 'time_asc' THEN time END ASC,
            CASE :orderKey WHEN 'time_desc' THEN time END DESC
    """)
    fun getNotes(orderKey: String) : Flow<List<Note>>

    @Insert
    fun insertNote(note : Note)
    @Update
    fun updateNote(note : Note)
    @Delete
    fun deleteNote(note : Note)
}