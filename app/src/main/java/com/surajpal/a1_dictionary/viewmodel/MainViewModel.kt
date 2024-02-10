package com.surajpal.a1_dictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surajpal.a1_dictionary.model.WordResult
import com.surajpal.a1_dictionary.repository.MeaningRepository
import kotlinx.coroutines.launch

// MainViewModel.kt
class MainViewModel(private val repository: MeaningRepository) : ViewModel() {

    // LiveData for search results
    private val _meaningData = MutableLiveData<WordResult?>()
    val meaningData: LiveData<WordResult?> = _meaningData

    // LiveData for loading status
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData for error message
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Function to search for word meaning
    fun getMeaning(word: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getMeaning(word)
                _meaningData.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Error getting meaning: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Function to save word result to database
    suspend fun saveDataToRoom(wordResult: WordResult) {
        repository.saveWordResult(wordResult)
    }

    suspend fun deleteDataFromRoom(wordResult: WordResult) {
        repository.deleteWordResult(wordResult)
    }

    // Function to get all saved word results from database
    private fun getAllWordResults(): LiveData<List<WordResult>> {
        return repository.getAllWordResults()
    }

    val wordResults: LiveData<List<WordResult>> = getAllWordResults()
}
