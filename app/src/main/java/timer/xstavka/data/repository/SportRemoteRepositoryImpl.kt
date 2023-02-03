package timer.xstavka.data.repository

import timer.xstavka.data.mapper.toRemoteData
import timer.xstavka.domain.model.RemoteData
import timer.xstavka.domain.repository.SportRemoteRepository
import timer.xstavka.utils.Resource
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.inject.Inject

class SportRemoteRepositoryImpl @Inject constructor() :

    SportRemoteRepository {

    private val remoteConfig = Firebase.remoteConfig
    override fun initConfigs() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Resource.Success(remoteConfig.getString("url").toRemoteData())
                    //Resource.Success(it.result)
                } else {
                    Resource.Error(it.result.toString())
                }
            }.addOnFailureListener {
                Resource.Error(it.message?: "Unknown error", null)
            }
    }

    override fun getConfigs(): Resource<RemoteData> {
        return try {
            Resource.Success(remoteConfig.getString("url").toRemoteData())
        } catch (e:java.lang.Exception) {
            e.printStackTrace()
            Resource.Error(e.message?: "Unknown error")
        }
    }
}