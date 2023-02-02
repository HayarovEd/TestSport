package timer.xstavka.domain.repository

import timer.xstavka.domain.model.RemoteData
import timer.xstavka.utils.Resource

interface SportRemoteRepository {
    fun initConfigs()

    fun getConfigs(): Resource<RemoteData>
}