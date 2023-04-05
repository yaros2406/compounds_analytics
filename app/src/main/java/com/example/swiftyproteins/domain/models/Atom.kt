package com.example.swiftyproteins.domain.models

class Atom(
    val id: String,
    val name: String,
    val coordinate: Coordinate,
    val base: BaseAtom,
    val connectList: List<String>
) {
    class Coordinate(
        val x: Float,
        val y: Float,
        val z: Float
    )

    enum class BaseAtom {
        C, O, H, P, N, F, S, BR, B, CL, MO, I, AU, V, CO, BA, MG, CU, CA,
        AS, CD, CS, EU, FE, GA, HG, U, K, LA, LI, MN, SE, NA, NI, PB, PD,
        PT, W, RB, RH, RU, SR, TB, TL, XE, YB, ZN, OTHER
    }
}