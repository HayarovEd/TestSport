package com.edurda77.testsport.data.repository

import com.edurda77.testsport.data.mapper.toRemoteData
import com.edurda77.testsport.domain.model.RemoteData
import com.edurda77.testsport.domain.repository.SportRemoteRepository
import com.edurda77.testsport.utils.Resource
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
                    Resource.Success(it.result)
                } else {
                    Resource.Error(it.result.toString())
                }
            }.addOnFailureListener {
                Resource.Error(it.message?: "Unknown error", null)
            }
    }

    override fun getConfigs(): Resource<RemoteData> {
        return try {
            Resource.Success(remoteConfig.getString("message").toRemoteData())
        } catch (e:java.lang.Exception) {
            e.printStackTrace()
            Resource.Error(e.message?: "Unknown error")
        }
    }
}