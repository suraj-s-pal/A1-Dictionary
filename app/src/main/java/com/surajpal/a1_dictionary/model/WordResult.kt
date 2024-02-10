package com.surajpal.a1_dictionary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_results")
data class WordResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val word: String,
    val phonetic: String?,
    val meanings: List<Meaning>
)
