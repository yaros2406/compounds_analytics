package com.example.swiftyproteins.presentation.models

import com.example.swiftyproteins.data.model.AtomsInfo

class ModelAtomInfo(
    val atomInfo: AtomsInfo.AtomInfo
) {
    val atomFullName: String = "${atomInfo.name} (${atomInfo.symbol})"
    val isBoilingPointVisible: Boolean = atomInfo.boil != null
    val isMeltingPointVisible: Boolean = atomInfo.melt != null
}