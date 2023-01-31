package com.edurda77.testsport.presentation

import com.edurda77.testsport.domain.model.Note
import com.edurda77.testsport.domain.model.RemoteData
import com.edurda77.testsport.utils.NO_INTERNET

sealed class MaintActivityState {
    class SuccessConnect(
        val remoteData: RemoteData
    ) : MaintActivityState()
    class NoteWork (val notes: List<Note>) : MaintActivityState()
    class Error(val message: String) : MaintActivityState()
    class NoInternet(val message: String = NO_INTERNET) : MaintActivityState()
    object Loading: MaintActivityState()
}
