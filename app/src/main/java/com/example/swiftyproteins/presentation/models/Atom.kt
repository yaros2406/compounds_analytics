package com.example.swiftyproteins.presentation.models

import androidx.annotation.ColorRes
import com.google.ar.sceneform.math.Vector3

class Atom(
    val name: String,
    val coordinate: Vector3,
    @ColorRes val colorResId: Int
)