package com.example.swiftyproteins.data.model

import com.google.gson.annotations.SerializedName

data class AtomsInfo(
    val elements: List<AtomInfo>,
) {
    class AtomInfo(
        val name: String,
        @SerializedName("atomic_mass")
        val atomicMass: String,
        val boil: String?,
        val color: String?,
        val melt: String?,
        val summary: String,
        val symbol: String,
    )
}