package com.edurda77.testsport.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edurda77.testsport.domain.model.Note
import com.edurda77.testsport.domain.repository.SportLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogAddNoteViewModel @Inject constructor(private val localRepo: SportLocalRepository) : ViewModel() {

    fun addNote(note: Note) {
        viewModelScope.launch {
            localRepo.addNote(note)
        }
    }
}