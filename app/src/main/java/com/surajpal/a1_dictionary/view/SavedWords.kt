package com.surajpal.a1_dictionary.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.surajpal.a1_dictionary.adapters.WordResultAdapter
import com.surajpal.a1_dictionary.api.RetrofitInstance
import com.surajpal.a1_dictionary.databinding.ActivitySavedWordsBinding
import com.surajpal.a1_dictionary.db.AppDatabase
import com.surajpal.a1_dictionary.model.WordResult
import com.surajpal.a1_dictionary.repository.MeaningRepository
import com.surajpal.a1_dictionary.viewmodel.MainViewModel
import com.surajpal.a1_dictionary.viewmodel.MainViewModelFactory

class SavedWords : AppCompatActivity() {

    lateinit var binding: ActivitySavedWordsBinding
    private lateinit var wordResultAdapter: WordResultAdapter
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val wordResultDao = AppDatabase.getDatabase(applicationContext).wordResultDao()
        val repository = MeaningRepository(RetrofitInstance.dictionaryApi, wordResultDao)
        mainViewModel = MainViewModelFactory(repository).create(MainViewModel::class.java)

        wordResultAdapter = WordResultAdapter(emptyList<WordResult>().toMutableList())

        wordResultAdapter.onDeleteItemClickListener = object : WordResultAdapter.OnDeleteItemClickListener {
            override suspend fun onDeleteItemClick(position: Int) {
                val wordResultToDelete = wordResultAdapter.wordResults[position]
                mainViewModel.deleteDataFromRoom(wordResultToDelete)
            }
        }

        binding.saveRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@SavedWords)
            adapter = wordResultAdapter
        }

        mainViewModel.wordResults.observe(this, Observer { wordResults ->
            wordResultAdapter.setData(wordResults)
        })

        binding.searchBtn.setOnClickListener {
            val word = binding.searchInput.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("WORD_EXTRA", word)
            startActivity(intent)
        }


    }
}