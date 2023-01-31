package com.edurda77.testsport.data.mapper

import com.edurda77.testsport.domain.model.RemoteData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toRemoteData() : RemoteData {
    return RemoteData(urlPath = this)
}

fun LocalDateTime.convertToString(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")
    return this.format(formatter)
}