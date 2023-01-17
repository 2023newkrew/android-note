package com.survivalcoding.noteapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String = "Empty Title",
    val content: String = "Empty Content",
    val colorCode: Int = 0,
    val time: Long = 0L,
) : Parcelable