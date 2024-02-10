package com.surajpal.a1_dictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surajpal.a1_dictionary.databinding.SavedWordsRecyclerviewBinding
import com.surajpal.a1_dictionary.model.WordResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordResultAdapter(var wordResults: MutableList<WordResult>) :
    RecyclerView.Adapter<WordResultAdapter.WordResultViewHolder>() {

    interface OnDeleteItemClickListener {
        suspend fun onDeleteItemClick(position: Int)
    }

    var onDeleteItemClickListener: OnDeleteItemClickListener? = null


    inner class WordResultViewHolder(private val binding: SavedWordsRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(wordResult: WordResult) {
            binding.word.text = wordResult.word
            binding.definitionsTextview.text =
                wordResult.meanings.firstOrNull()?.definitions?.firstOrNull()?.definition

            // Set up delete button click listener
            binding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                   CoroutineScope(Dispatchers.IO).launch {
                       onDeleteItemClickListener?.onDeleteItemClick(position)
                   }
                }
            }
        }
    }

    // Listener to handle delete button click
    var onDeleteClickListener: ((Int) -> Unit)? = null

    fun setData(wordResults: List<WordResult>) {
        this.wordResults.clear()
        this.wordResults.addAll(wordResults)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SavedWordsRecyclerviewBinding.inflate(inflater, parent, false)
        return WordResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordResultViewHolder, position: Int) {
        holder.bind(wordResults[position])
    }

    override fun getItemCount(): Int {
        return wordResults.size
    }
}



