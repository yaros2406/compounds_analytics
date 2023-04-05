package com.example.swiftyproteins.presentation.scene.nodes

import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.ux.TransformationSystem

class DragTransformableNode(val radius: Float, transformationSystem: TransformationSystem) :
    TransformableNode(transformationSystem) {
    val dragRotationController = DragRotationController((this), transformationSystem.dragRecognizer)
//    val scale = ScaleController(this, transformationSystem.pinchRecognizer)
}