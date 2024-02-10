package com.surajpal.a1_dictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surajpal.a1_dictionary.databinding.MeaningRecyclerRowBinding
import com.surajpal.a1_dictionary.model.Meaning


class MeaningAdapter(private var meaningList: List<Meaning>? = null) :
    RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    inner class MeaningViewHolder(private val binding: MeaningRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meaning: Meaning) {
            with(binding) {
                partOfSpeechTextview.text = meaning.partOfSpeech
                definitionsTextview.text = meaning.definitions.joinToString("\n\n") { "${it.definition}" }
                synonymsTextview.apply {
                    text = meaning.synonyms.joinToString(", ")
                    visibility = if (meaning.synonyms.isNotEmpty()) View.VISIBLE else View.GONE
                }
                antonymsTextview.apply {
                    text = meaning.antonyms.joinToString(", ")
                    visibility = if (meaning.antonyms.isNotEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    fun updateNewData(newMeaningList: List<Meaning>?) {
        meaningList = newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding =
            MeaningRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        meaningList?.get(position)?.let { holder.bind(it) }
    }
}
