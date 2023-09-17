package com.angelika.rickmortyapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angelika.rickmortyapi.databinding.ItemRickAndMortyBinding
import com.angelika.rickmortyapi.models.CharacterModel
import com.bumptech.glide.Glide

class CharacterAdapter :
    ListAdapter<CharacterModel, CharacterAdapter.ViewHolder>(DiffUtilCallback()) {

     inner class ViewHolder(private val binding: ItemRickAndMortyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(characterModel: CharacterModel) {
            Glide.with(binding.itemCharacterImage).load(characterModel.image)
                .into(binding.itemCharacterImage)
            binding.itemCharacterName.text = characterModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRickAndMortyBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


    class DiffUtilCallback : DiffUtil.ItemCallback<CharacterModel>() {

        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }
    }
}