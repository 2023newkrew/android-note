package com.survivalcoding.noteapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String = "Empty Title",
    val content: String = "Empty Content",
    val colorCode: Int = 0,
    val time: Long = 0L,
)