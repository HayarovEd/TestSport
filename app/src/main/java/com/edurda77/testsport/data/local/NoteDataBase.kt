package com.edurda77.testsport.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edurda77.testsport.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class CryptoDataBase: RoomDatabase() {
    abstract val dao: NoteDao
}