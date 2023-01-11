package com.survivalcoding.noteapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo var title: String,
    @ColumnInfo var content: String,
    @ColumnInfo var colorCode: Int,
    @ColumnInfo var time: Long,
)