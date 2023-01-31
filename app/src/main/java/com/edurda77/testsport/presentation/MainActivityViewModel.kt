package com.edurda77.testsport.presentation

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.BuildConfig
import com.edurda77.testsport.domain.model.RemoteData
import com.edurda77.testsport.domain.repository.SportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(/*private val repo: SportRepository*/) : ViewModel() {
    private val _showData = MutableLiveData<MaintActivityState>(MaintActivityState.Loading)
    val showData = _showData


    fun getFromLocal(pathUrl: String = "", checkedInternetConnection: Boolean) {
        if (pathUrl != "") {
            if (checkedInternetConnection) {
                _showData.value = MaintActivityState.SuccessConnect(remoteData = RemoteData(pathUrl))
            } else {
                _showData.value = MaintActivityState.NoInternet()
            }
        } else {
            _showData.value = MaintActivityState.SuccessConnect(remoteData = RemoteData("https://stackoverflow.com/questions/53532406/activenetworkinfo-type-is-deprecated-in-api-level-28"))
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
}