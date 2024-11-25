package com.example.mobfirstlaba.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobfirstlaba.R
import com.example.mobfirstlaba.models.CharacterModel

class CharacterAdapter :
    ListAdapter<CharacterModel, CharacterAdapter.CharacterViewHolder>(CharacterDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val cultureTextView: TextView = itemView.findViewById(R.id.cultureTextView)
        private val titlesTextView: TextView = itemView.findViewById(R.id.titlesTextView)
        private val playedByTextView: TextView = itemView.findViewById(R.id.playedByTextView)

        fun bind(character: CharacterModel) {
            nameTextView.text = character.name ?: "Unknown"
            cultureTextView.text = character.culture ?: "Unknown"
            titlesTextView.text = character.titles.joinToString(", ").ifEmpty { "No Titles" }
            playedByTextView.text = character.playedBy.joinToString(", ").ifEmpty { "Unknown Actor" }

        }
    }



    class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }
    }
}
