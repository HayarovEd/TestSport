package com.edurda77.testsport.presentation

import com.edurda77.testsport.domain.model.Note
import com.edurda77.testsport.domain.model.RemoteData

sealed class MaintActivityState {
    data class Success(
        val notes: List<Note> = emptyList(),
        val remoteData: RemoteData? = null
    ) : MaintActivityState()

    data class Error(val message: String? = null) : MaintActivityState()
    object Loading: MaintActivityState()
}
