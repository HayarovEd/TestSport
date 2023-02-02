package com.edurda77.testsport.presentation

import androidx.lifecycle.ViewModel
import com.edurda77.testsport.domain.repository.SportRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(private val sportRemoteRepository: SportRemoteRepository) : ViewModel(){

    fun getData () {
        sportRemoteRepository.getConfigs()
    }
}