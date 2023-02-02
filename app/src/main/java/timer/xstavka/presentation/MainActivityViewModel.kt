package timer.xstavka.presentation

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.BuildConfig
import timer.xstavka.domain.model.Note
import timer.xstavka.domain.model.RemoteData
import timer.xstavka.domain.repository.SportLocalRepository
import timer.xstavka.domain.repository.SportRemoteRepository
import timer.xstavka.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val localRepo: SportLocalRepository,
    private val sportRemoteRepository: SportRemoteRepository
) :
    ViewModel() {
    private val _showData = MutableLiveData<MaintActivityState>(MaintActivityState.Loading)
    val showData = _showData


    fun getFromLocal(pathUrl: String = "", checkedInternetConnection: Boolean, checkSim: Boolean) {
        if (pathUrl != "") {
            if (checkedInternetConnection) {
                _showData.value =
                    MaintActivityState.SuccessConnect(remoteData = RemoteData(pathUrl))
            } else {
                _showData.value = MaintActivityState.NoInternet()
            }
        } else {
            when (val remoteData = sportRemoteRepository.getConfigs()) {
                is Resource.Error -> {
                    _showData.value =
                        MaintActivityState.Error(message = remoteData.message ?: "Unknown error")
                }
                is Resource.Success -> {
                    val dataRemote = remoteData.data?.urlPath
                    if (checkIsEmu() || /*!checkSim ||*/ dataRemote == "") {
                        viewModelScope.launch {
                            localRepo.getNotes().flowOn(Dispatchers.IO)
                                .collect {
                                    _showData.value = MaintActivityState.NoteWork(it)
                                }
                        }
                    } else {
                        _showData.value =
                            remoteData.data.let {
                                MaintActivityState.SuccessConnect(
                                    RemoteData(
                                        dataRemote ?: ""
                                    )
                                )
                            }
                    }
                }
            }
        }
    }

    private fun checkIsEmu(): Boolean {
        if (BuildConfig.DEBUG) return false
        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        var result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || Build.BRAND.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
        if (result) return true
        result = result or (Build.BRAND.startsWith("generic") &&
                Build.DEVICE.startsWith("generic"))
        if (result) return true
        result = result or ("google_sdk" == buildProduct)
        return result
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            localRepo.deleteNote(note.id)
        }
    }
}