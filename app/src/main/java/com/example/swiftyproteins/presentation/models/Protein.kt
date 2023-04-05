package com.example.swiftyproteins.presentation.models

import com.google.ar.sceneform.math.Vector3
import java.lang.Float.max
import kotlin.math.absoluteValue
import kotlin.math.cos

class Protein(
    val atoms: List<Atom> = emptyList(),
    val atomConnections: List<AtomConnection> = emptyList()
) {
//    val cameraPosition: Vector3

//    init {
//        cameraPosition = getPositionCamera()
//    }

    private fun getPositionCamera(): Vector3 {
        if (atoms.isNotEmpty()) {
            var maxX: Float = atoms[0].coordinate.x
            var maxY: Float = atoms[0].coordinate.y
            var minX: Float = atoms[0].coordinate.x
            var minY: Float = atoms[0].coordinate.y
            var minZ: Float = atoms[0].coordinate.z
            atoms.forEach { atom ->
                when {
                    atom.coordinate.x < minX -> minX = atom.coordinate.x
                    atom.coordinate.x > maxX -> maxX = atom.coordinate.x
                }
                when {
                    atom.coordinate.y < minY -> minY = atom.coordinate.y
                    atom.coordinate.y > maxY -> maxY = atom.coordinate.y
                }
                if (atom.coordinate.z < minZ) {
                    minZ = atom.coordinate.z
                }
            }
            val distX: Float = (maxX - minX).absoluteValue
            val distY: Float = (maxY - minZ).absoluteValue
            val distMax: Float = max(distX, distY)
            val cameraX: Float = maxX - distX.div(2f)
            val cameraY: Float = maxY - distY.div(2f)
            val cameraZ: Float = minZ - distMax * cos(30f)
            return Vector3(cameraX, cameraY, cameraZ)
        }
        return Vector3(0f, 0f, 0f)
    }
}