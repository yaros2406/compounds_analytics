package com.example.swiftyproteins.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.swiftyproteins.presentation.adapters.diffutilcallbacks.LigandItemDiff
import com.example.swiftyproteins.presentation.adapters.viewholders.LigandViewHolder
import com.example.swiftyproteins.databinding.ItemLigandBinding

class LigandListAdapter(private val ligandClickListener: (String) -> Unit) :
    ListAdapter<String, LigandViewHolder>(LigandItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LigandViewHolder(
            ItemLigandBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: LigandViewHolder, position: Int) {
        holder.bind(getItem(position), ligandClickListener)
    }
}