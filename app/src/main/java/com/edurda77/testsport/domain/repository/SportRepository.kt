package com.edurda77.testsport.domain.repository

import com.edurda77.testsport.domain.model.Note
import com.edurda77.testsport.domain.model.RemoteData
import com.edurda77.testsport.utils.Resource

interface SportRepository {
    suspend fun getNotes() : List<Note>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(id:Int)
    suspend fun updateNote(note: Note)
    suspend fun getConfigs(): Resource<RemoteData>
}