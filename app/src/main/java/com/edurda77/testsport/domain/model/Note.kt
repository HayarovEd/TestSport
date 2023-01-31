package com.edurda77.testsport.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edurda77.testsport.utils.*
import java.time.LocalDateTime

@Entity (tableName = NOTE_TABLE)
data class Note(
    @ColumnInfo(name = NOTE_TITLE)
    val title: String,
    @ColumnInfo(name = NOTE_CONTENT)
    val content: String,
    @ColumnInfo(name = DATE_RECORD)
    val timestamp: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NOTE_ID)
    val id: Int
)
