package com.edurda77.testsport.data.mapper

import com.edurda77.testsport.domain.model.RemoteData

fun String.toRemoteData() : RemoteData {
    return RemoteData(urlPath = this)
}