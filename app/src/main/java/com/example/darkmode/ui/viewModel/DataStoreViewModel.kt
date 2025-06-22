package com.example.darkmode.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.darkmode.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    val isDarkMode = repository.isDarkMode

    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            repository.setDarkMode(isDarkMode)
        }
    }


}