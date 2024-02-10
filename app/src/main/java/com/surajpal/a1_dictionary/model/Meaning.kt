package com.surajpal.a1_dictionary.model

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: List<String>,
    val antonyms: List<String>
)