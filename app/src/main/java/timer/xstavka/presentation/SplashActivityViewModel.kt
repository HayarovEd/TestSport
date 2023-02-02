package timer.xstavka.presentation

import androidx.lifecycle.ViewModel
import timer.xstavka.domain.repository.SportRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(private val sportRemoteRepository: SportRemoteRepository) : ViewModel(){

    fun getData () {
        sportRemoteRepository.getConfigs()
    }
}