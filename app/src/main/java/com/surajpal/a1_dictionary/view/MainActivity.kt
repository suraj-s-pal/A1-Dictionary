package com.surajpal.a1_dictionary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.surajpal.a1_dictionary.adapters.MeaningAdapter
import com.surajpal.a1_dictionary.api.RetrofitInstance
import com.surajpal.a1_dictionary.databinding.ActivityMainBinding
import com.surajpal.a1_dictionary.db.AppDatabase
import com.surajpal.a1_dictionary.model.WordResult
import com.surajpal.a1_dictionary.repository.MeaningRepository
import com.surajpal.a1_dictionary.viewmodel.MainViewModel
import com.surajpal.a1_dictionary.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter: MeaningAdapter by lazy { MeaningAdapter(emptyList()) }
    lateinit var mainViewModel: MainViewModel // Declare MainViewModel variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wordResultDao = AppDatabase.getDatabase(applicationContext).wordResultDao()
        val repository = MeaningRepository(RetrofitInstance.dictionaryApi, wordResultDao)
        mainViewModel = MainViewModelFactory(repository).create(MainViewModel::class.java)

        val receivedWord = intent.getStringExtra("WORD_EXTRA")
        if (receivedWord != null) {
            searchWord(receivedWord)
        }

        binding.searchBtn.setOnClickListener {
            val word = binding.searchInput.text.toString()
            searchWord(word)
        }

        binding.meaningRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.meaningRecyclerView.adapter = adapter

        binding.saveButton.setOnClickListener {
            // Call method to save data into Room
            saveDataToRoom()
            Toast.makeText(this, "Word saved", Toast.LENGTH_SHORT).show()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        mainViewModel.meaningData.observe(this) { meaning ->
            meaning?.let {
                setUI(it)
            }
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            setInProgress(isLoading)
        }

        mainViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.searchBtn.visibility = View.INVISIBLE
            binding.saveButton.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.searchBtn.visibility = View.VISIBLE
            binding.saveButton.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun setUI(response: WordResult) {
        binding.wordTextview.text = response.word
        binding.phoneticTextview.text = response.phonetic
        adapter.updateNewData(response.meanings)
    }

    private fun saveDataToRoom() {
        val response = mainViewModel.meaningData.value
        response?.let {
            CoroutineScope(Dispatchers.IO).launch{
                mainViewModel.saveDataToRoom(it)
            }

        }
    }

    private fun searchWord(inputWord :String){
        mainViewModel.getMeaning(inputWord)
    }

}
