package com.surajpal.a1_dictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.surajpal.a1_dictionary.repository.MeaningRepository

class MainViewModelFactory(private val repository: MeaningRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}