package com.example.swiftyproteins.domain.mapper

import com.example.swiftyproteins.domain.models.Atom

class ProteinMapper {
    fun map(protein: List<Atom>): List<Atom> {
        val newProtein = mutableListOf<Atom>()
        protein.forEach { currentAtom ->
            if (currentAtom.connectList.isEmpty()) {
                val newConnections = mutableListOf<String>()
                protein.filter { atom ->
                    atom.connectList.contains(currentAtom.id)
                }.forEach { atom ->
                    newConnections.add(atom.id)
                }
                if (newConnections.isNotEmpty()) {
                    newProtein.add(
                        Atom(
                            currentAtom.id,
                            currentAtom.name,
                            currentAtom.coordinate,
                            currentAtom.base,
                            newConnections
                        )
                    )
                } else {
                    newProtein.add(currentAtom)
                }
            } else {
                newProtein.add(currentAtom)
            }
        }
        return newProtein
    }
}