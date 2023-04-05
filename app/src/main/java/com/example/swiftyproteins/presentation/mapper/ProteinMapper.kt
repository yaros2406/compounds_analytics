package com.example.swiftyproteins.presentation.mapper

import com.example.swiftyproteins.R
import com.example.swiftyproteins.presentation.models.Atom
import com.example.swiftyproteins.presentation.models.AtomConnection
import com.example.swiftyproteins.presentation.models.Protein
import com.example.swiftyproteins.presentation.toVec3
import com.google.ar.sceneform.math.Vector3
import com.example.swiftyproteins.domain.models.Atom as DomainAtom

class ProteinMapper {
    fun mapAtomName(name: String): String =
        name.split('_')[0]

    fun map(ligand: List<DomainAtom>, isAtomHVisible: Boolean = false): Protein {
        val atoms = mutableListOf<Atom>()
        val atomsConnection = mutableListOf<AtomConnection>()
        ligand.mapIndexed { index, atom ->
            if (isAtomHVisible || (!isAtomHVisible && atom.base != DomainAtom.BaseAtom.H)) {
                val coordinateAtom: Vector3 = atom.coordinate.toVec3()
                atoms.add(
                    Atom(
                        ("${atom.base.name}_${atom.name}"),
                        coordinateAtom,
                        parseColor(atom.base)
                    )
                )
                val atomConnections: List<AtomConnection> = getAtomConnections(
                    coordinateAtom, atom.connectList, index, ligand, isAtomHVisible
                )
                if (atomConnections.isNotEmpty()) {
                    atomsConnection.addAll(atomConnections)
                }
            }
        }
        return Protein(atoms, atomsConnection)
    }

    private fun parseColor(baseAtom: DomainAtom.BaseAtom): Int =
        when (baseAtom) {
            DomainAtom.BaseAtom.C -> R.color.colorAtomC
            DomainAtom.BaseAtom.O -> R.color.colorAtomO
            DomainAtom.BaseAtom.H -> R.color.colorAtomH
            DomainAtom.BaseAtom.N -> R.color.colorAtomN
            DomainAtom.BaseAtom.P -> R.color.colorAtomP
            DomainAtom.BaseAtom.F -> R.color.colorAtomF
            DomainAtom.BaseAtom.S -> R.color.colorAtomS
            DomainAtom.BaseAtom.BR -> R.color.colorAtomBR
            DomainAtom.BaseAtom.B -> R.color.colorAtomB
            DomainAtom.BaseAtom.CL -> R.color.colorAtomCL
            DomainAtom.BaseAtom.MO -> R.color.colorAtomMO
            DomainAtom.BaseAtom.I -> R.color.colorAtomI
            DomainAtom.BaseAtom.AU -> R.color.colorAtomAU
            DomainAtom.BaseAtom.V -> R.color.colorAtomV
            DomainAtom.BaseAtom.CO -> R.color.colorAtomCO
            DomainAtom.BaseAtom.BA -> R.color.colorAtomBA
            DomainAtom.BaseAtom.MG -> R.color.colorAtomMG
            DomainAtom.BaseAtom.CU -> R.color.colorAtomCU
            DomainAtom.BaseAtom.CA -> R.color.colorAtomCA
            DomainAtom.BaseAtom.AS -> R.color.colorAtomAS
            DomainAtom.BaseAtom.CD -> R.color.colorAtomCD
            DomainAtom.BaseAtom.CS -> R.color.colorAtomCS
            DomainAtom.BaseAtom.EU -> R.color.colorAtomEU
            DomainAtom.BaseAtom.FE -> R.color.colorAtomFE
            DomainAtom.BaseAtom.GA -> R.color.colorAtomGA
            DomainAtom.BaseAtom.HG -> R.color.colorAtomHG
            DomainAtom.BaseAtom.U -> R.color.colorAtomU
            DomainAtom.BaseAtom.K -> R.color.colorAtomK
            DomainAtom.BaseAtom.LA -> R.color.colorAtomLA
            DomainAtom.BaseAtom.LI -> R.color.colorAtomLI
            DomainAtom.BaseAtom.MN -> R.color.colorAtomMN
            DomainAtom.BaseAtom.SE -> R.color.colorAtomSE
            DomainAtom.BaseAtom.NA -> R.color.colorAtomNA
            DomainAtom.BaseAtom.NI -> R.color.colorAtomNI
            DomainAtom.BaseAtom.PB -> R.color.colorAtomPB
            DomainAtom.BaseAtom.PD -> R.color.colorAtomPD
            DomainAtom.BaseAtom.PT -> R.color.colorAtomPT
            DomainAtom.BaseAtom.W -> R.color.colorAtomW
            DomainAtom.BaseAtom.RB -> R.color.colorAtomRB
            DomainAtom.BaseAtom.RH -> R.color.colorAtomRH
            DomainAtom.BaseAtom.RU -> R.color.colorAtomRU
            DomainAtom.BaseAtom.SR -> R.color.colorAtomSR
            DomainAtom.BaseAtom.TB -> R.color.colorAtomTB
            DomainAtom.BaseAtom.TL -> R.color.colorAtomTL
            DomainAtom.BaseAtom.XE -> R.color.colorAtomXE
            DomainAtom.BaseAtom.YB -> R.color.colorAtomYB
            DomainAtom.BaseAtom.ZN -> R.color.colorAtomZN
            DomainAtom.BaseAtom.OTHER -> R.color.colorOther
        }

    private fun getAtomConnections(
        coordinateTop: Vector3,
        connections: List<String>,
        currentAtomIndex: Int,
        ligand: List<DomainAtom>,
        isAtomHVisible: Boolean,
    ): List<AtomConnection> =
        connections.mapNotNull { atomId ->
            if (atomId.toInt() > currentAtomIndex.minus((1))) {
                ligand.find { secondAtom ->
                    isItNeededSecondAtomInConnection(secondAtom, atomId, isAtomHVisible)
                }?.coordinate?.toVec3()?.let { coordinateBottom ->
                    AtomConnection(
                        coordinateTop,
                        coordinateBottom,
                        R.color.atom_connection
                    )
                }
            } else {
                null
            }
        }

    private fun isItNeededSecondAtomInConnection(
        secondAtom: DomainAtom,
        firstAtomId: String,
        isAtomHVisible: Boolean
    ): Boolean =
        secondAtom.id == firstAtomId &&
                (isAtomHVisible || (!isAtomHVisible && secondAtom.base != DomainAtom.BaseAtom.H))
}