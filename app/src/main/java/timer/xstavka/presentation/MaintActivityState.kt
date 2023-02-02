package timer.xstavka.presentation

import timer.xstavka.domain.model.Note
import timer.xstavka.domain.model.RemoteData
import timer.xstavka.utils.NO_INTERNET

sealed class MaintActivityState {
    class SuccessConnect(
        val remoteData: RemoteData
    ) : MaintActivityState()
    class NoteWork (val notes: List<Note>) : MaintActivityState()
    class Error(val message: String) : MaintActivityState()
    class NoInternet(val message: String = NO_INTERNET) : MaintActivityState()
    object Loading: MaintActivityState()
}
