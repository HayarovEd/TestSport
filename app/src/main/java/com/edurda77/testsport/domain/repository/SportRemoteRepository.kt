package com.edurda77.testsport.domain.repository

import com.edurda77.testsport.domain.model.RemoteData
import com.edurda77.testsport.utils.Resource

interface SportRemoteRepository {
    fun initConfigs()

    fun getConfigs(): Resource<RemoteData>
}