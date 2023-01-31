package com.edurda77.testsport.data.repository

import com.edurda77.testsport.data.local.NoteDataBase
import com.edurda77.testsport.data.mapper.toRemoteData
import com.edurda77.testsport.domain.model.Note
import com.edurda77.testsport.domain.model.RemoteData
import com.edurda77.testsport.domain.repository.SportRepository
import com.edurda77.testsport.utils.Resource
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import javax.inject.Inject

class SportRepositoryImpl @Inject constructor(
    private val mFirebaseRemoteConfig: FirebaseRemoteConfig,
    db: NoteDataBase
) : SportRepository {
    private val dao = db.dao

    override suspend fun getNotes(): List<Note> {
        return dao.getNotes()
    }

    override suspend fun addNote(note: Note) {
        dao.addNote(note)
    }

    override suspend fun deleteNote(id: Int) {
        dao.deleteNote(id)
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(
            id = note.id,
            title = note.title,
            date = note.timestamp,
            content = note.content
        )
    }

    override suspend fun getConfigs(): Resource<RemoteData> {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Resource.Success(it.result)
                } else {
                    Resource.Error(it.result.toString())
                }
            }.addOnFailureListener {
                Resource.Error(it.message?: "Unknown error", null)
            }
        return try {
            Resource.Success(mFirebaseRemoteConfig.getString("message").toRemoteData())
        } catch (e:java.lang.Exception) {
            e.printStackTrace()
            Resource.Error(e.message?: "Неизвестная ошибка")
        }

    }
}