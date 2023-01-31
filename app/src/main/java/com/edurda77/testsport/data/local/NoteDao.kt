package com.edurda77.testsport.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edurda77.testsport.domain.model.Note
import com.edurda77.testsport.utils.*
import java.time.LocalDateTime

@Dao
interface NoteDao {
    @Insert(entity = Note::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note:Note)

    @Query("DELETE FROM $NOTE_TABLE WHERE $NOTE_ID=:id")
    suspend fun deleteNote(id: Int)

    @Query("UPDATE $NOTE_TABLE SET $NOTE_TITLE=:title, $NOTE_CONTENT=:content, $DATE_RECORD =:date  WHERE $NOTE_ID=:id")
    suspend fun updateNote(id: Int, title:String, content: String, date: String)

    @Query("SELECT * FROM $NOTE_TABLE")
    suspend fun getNotes(): List<Note>
}