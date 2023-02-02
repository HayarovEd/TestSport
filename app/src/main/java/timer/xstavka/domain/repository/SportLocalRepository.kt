package timer.xstavka.domain.repository

import timer.xstavka.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface SportLocalRepository {
    suspend fun getNotes() : Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(id:Int)
    suspend fun updateNote(note: Note)
}