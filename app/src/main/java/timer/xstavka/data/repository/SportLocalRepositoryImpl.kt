package timer.xstavka.data.repository

import timer.xstavka.data.local.NoteDataBase
import timer.xstavka.domain.model.Note
import timer.xstavka.domain.repository.SportLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportLocalRepositoryImpl @Inject constructor(
    db: NoteDataBase
) : SportLocalRepository {
    private val dao = db.dao

    override suspend fun getNotes(): Flow<List<Note>> {
        return flow {
           emit (dao.getNotes())
        }
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

    /*override suspend fun getConfigs(): Resource<RemoteData> {
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

    }*/
}