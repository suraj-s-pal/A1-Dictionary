package com.surajpal.a1_dictionary.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surajpal.a1_dictionary.model.WordResult

@Dao
interface WordResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wordResult: WordResult)

    @Delete
    suspend fun delete(wordResult: WordResult)


    @Query("SELECT * FROM word_results ORDER BY id DESC")
    fun getAllWordResults(): LiveData<List<WordResult>>
}