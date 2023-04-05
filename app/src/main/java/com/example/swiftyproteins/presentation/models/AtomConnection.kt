package com.example.swiftyproteins.presentation.models

import androidx.annotation.ColorRes
import com.google.ar.sceneform.math.Vector3

class AtomConnection(
    val coordinateTop: Vector3,
    val coordinateBottom: Vector3,
    @ColorRes val colorResId: Int
)