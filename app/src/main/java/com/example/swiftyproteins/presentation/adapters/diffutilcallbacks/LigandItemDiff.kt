package com.example.swiftyproteins.presentation.adapters.diffutilcallbacks

import androidx.recyclerview.widget.DiffUtil

class LigandItemDiff : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem
}