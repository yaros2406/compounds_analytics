package com.example.swiftyproteins.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.swiftyproteins.databinding.ItemLigandBinding

class LigandViewHolder(private val binding: ItemLigandBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(ligand: String, onLigandClick: (String) -> Unit) {
        binding.tvLigand.text = ligand
        binding.root.setOnClickListener {
            onLigandClick(ligand)
        }
    }
}