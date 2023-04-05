package com.example.swiftyproteins.data.mapper

import com.example.swiftyproteins.data.model.Atom
import com.example.swiftyproteins.data.model.Connection
import com.example.swiftyproteins.domain.models.Atom as DomainAtom
import com.example.swiftyproteins.domain.models.Atom.Coordinate
import com.example.swiftyproteins.presentation.splitOnlyWords

//TODO in data rename to ligand
class ProteinMapper {
    fun map(resource: List<String>): List<DomainAtom> {
        val atoms = mutableListOf<Atom>()
        val connects = mutableListOf<Connection>()
        resource.forEach { line ->
            val splitLine: List<String> = line.splitOnlyWords()
            val parameterName = splitLine[PARAMETER]
            val id = splitLine[ID]
            if (parameterName == ATOM) {
                atoms.add(parseAtom(id, splitLine))
            } else if (parameterName == CONNECT) {
                connects.add(parseConnect(id, splitLine))
            }
        }
        return map(atoms, connects)
    }

    private fun parseAtom(id: String, parameters: List<String>): Atom =
        Atom(
            id = id,
            name = parameters[ATOM_NAME],
            x = parameters[X],
            y = parameters[Y],
            z = parameters[Z],
            baseAtomName = parameters[BASE_ATOM_NAME]
        )

    private fun parseConnect(id: String, parameters: List<String>): Connection {
        val atomIdsConnection = mutableListOf<String>()
        parameters.forEachIndexed { index, parameter ->
            if (index >= FIRST_CONNECT_ID) {
                atomIdsConnection.add(parameter)
            }
        }
        return Connection(id, atomIdsConnection)
    }

    private fun map(atoms: List<Atom>, connections: List<Connection>): List<DomainAtom> =
        atoms.map { atom ->
            DomainAtom(
                id = atom.id,
                name = atom.name,
                coordinate = Coordinate(atom.x.toFloat(), atom.y.toFloat(), atom.z.toFloat()),
                base = parseBaseAtom(atom.baseAtomName),
                connectList = connections.find { connection ->
                    connection.id == atom.id
                }?.atomIdList ?: listOf()
            )
        }

    private fun parseBaseAtom(baseAtomName: String): DomainAtom.BaseAtom =
        when (baseAtomName) {
            ATOM_C -> DomainAtom.BaseAtom.C
            ATOM_O -> DomainAtom.BaseAtom.O
            ATOM_H -> DomainAtom.BaseAtom.H
            ATOM_P -> DomainAtom.BaseAtom.P
            ATOM_N -> DomainAtom.BaseAtom.N
            ATOM_F -> DomainAtom.BaseAtom.F
            ATOM_S -> DomainAtom.BaseAtom.S
            ATOM_BR -> DomainAtom.BaseAtom.BR
            ATOM_B -> DomainAtom.BaseAtom.B
            ATOM_CL -> DomainAtom.BaseAtom.CL
            ATOM_MO -> DomainAtom.BaseAtom.MO
            ATOM_I -> DomainAtom.BaseAtom.I
            ATOM_AU -> DomainAtom.BaseAtom.AU
            ATOM_V -> DomainAtom.BaseAtom.V
            ATOM_CO -> DomainAtom.BaseAtom.CO
            ATOM_BA -> DomainAtom.BaseAtom.BA
            ATOM_MG -> DomainAtom.BaseAtom.MG
            ATOM_CU -> DomainAtom.BaseAtom.CU
            ATOM_CA -> DomainAtom.BaseAtom.CA
            ATOM_AS -> DomainAtom.BaseAtom.AS
            ATOM_CD -> DomainAtom.BaseAtom.CD
            ATOM_CS -> DomainAtom.BaseAtom.CS
            ATOM_EU -> DomainAtom.BaseAtom.EU
            ATOM_FE -> DomainAtom.BaseAtom.FE
            ATOM_GA -> DomainAtom.BaseAtom.GA
            ATOM_HG -> DomainAtom.BaseAtom.HG
            ATOM_U -> DomainAtom.BaseAtom.U
            ATOM_K -> DomainAtom.BaseAtom.K
            ATOM_LA -> DomainAtom.BaseAtom.LA
            ATOM_LI -> DomainAtom.BaseAtom.LI
            ATOM_MN -> DomainAtom.BaseAtom.MN
            ATOM_SE -> DomainAtom.BaseAtom.SE
            ATOM_NA -> DomainAtom.BaseAtom.NA
            ATOM_NI -> DomainAtom.BaseAtom.NI
            ATOM_PB -> DomainAtom.BaseAtom.PB
            ATOM_PD -> DomainAtom.BaseAtom.PD
            ATOM_PT -> DomainAtom.BaseAtom.PT
            ATOM_W -> DomainAtom.BaseAtom.W
            ATOM_RB -> DomainAtom.BaseAtom.RB
            ATOM_RU -> DomainAtom.BaseAtom.RU
            ATOM_SR -> DomainAtom.BaseAtom.SR
            ATOM_TB -> DomainAtom.BaseAtom.TB
            ATOM_TL -> DomainAtom.BaseAtom.TL
            ATOM_XE -> DomainAtom.BaseAtom.XE
            ATOM_YB -> DomainAtom.BaseAtom.YB
            ATOM_ZN -> DomainAtom.BaseAtom.ZN
            else -> DomainAtom.BaseAtom.OTHER
        }

    companion object {
        private const val PARAMETER = 0
        private const val ID = 1
        private const val ATOM_NAME = 2
        private const val X = 6
        private const val Y = 7
        private const val Z = 8
        private const val BASE_ATOM_NAME = 11
        private const val FIRST_CONNECT_ID = 2
        private const val ATOM = "ATOM"
        private const val CONNECT = "CONECT"
        private const val ATOM_C = "C"
        private const val ATOM_O = "O"
        private const val ATOM_H = "H"
        private const val ATOM_P = "P"
        private const val ATOM_N = "N"
        private const val ATOM_F = "F"
        private const val ATOM_S = "S"
        private const val ATOM_BR = "BR"
        private const val ATOM_B = "B"
        private const val ATOM_CL = "CL"
        private const val ATOM_MO = "MO"
        private const val ATOM_I = "I"
        private const val ATOM_V = "V"
        private const val ATOM_AU = "AU"
        private const val ATOM_CO = "CO"
        private const val ATOM_BA = "BA"
        private const val ATOM_MG = "MG"
        private const val ATOM_CU = "CU"
        private const val ATOM_CA = "CA"
        private const val ATOM_AS = "AS"
        private const val ATOM_CD = "CD"
        private const val ATOM_CS = "CS"
        private const val ATOM_EU = "EU"
        private const val ATOM_FE = "FE"
        private const val ATOM_GA = "GA"
        private const val ATOM_HG = "HG"
        private const val ATOM_U = "U"
        private const val ATOM_K = "K"
        private const val ATOM_LA = "LA"
        private const val ATOM_LI = "LI"
        private const val ATOM_MN = "MN"
        private const val ATOM_SE = "SE"
        private const val ATOM_NA = "NA"
        private const val ATOM_NI = "NI"
        private const val ATOM_PB = "PB"
        private const val ATOM_PD = "PD"
        private const val ATOM_PT = "PT"
        private const val ATOM_W = "W"
        private const val ATOM_RB = "RB"
        private const val ATOM_RU = "RU"
        private const val ATOM_SR = "SR"
        private const val ATOM_TB = "TB"
        private const val ATOM_TL = "TL"
        private const val ATOM_XE = "XE"
        private const val ATOM_YB = "YB"
        private const val ATOM_ZN = "ZN"
    }
}