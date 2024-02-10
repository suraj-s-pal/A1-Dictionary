package com.surajpal.a1_dictionary.repository

import androidx.lifecycle.LiveData
import com.surajpal.a1_dictionary.db.WordResultDao
import com.surajpal.a1_dictionary.model.WordResult

class MeaningRepository(private val dictionaryApi: DictionaryApi,
                        private val wordResultDao: WordResultDao) {

    suspend fun getMeaning(word: String): WordResult? {
        return try {
            val response = dictionaryApi.getMeaning(word)
            if (response.isSuccessful) {
                response.body()?.firstOrNull() // Return the first item from the list or null if the list is empty
            } else {
                null // Handle unsuccessful response (e.g., network error)
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveWordResult(wordResult: WordResult) {
        wordResultDao.insert(wordResult)
    }

    suspend fun deleteWordResult(wordResult: WordResult) {
        wordResultDao.delete(wordResult)
    }

    fun getAllWordResults(): LiveData<List<WordResult>> {
        return wordResultDao.getAllWordResults()
    }
}