package com.makeus.daycarat.data.data

data class GemCount(
    val communication:Int = 0,
    val problemSolving:Int= 0,
    val creativity:Int=0,
    val challengeSpirit:Int=0,
    val proficiency:Int=0,
    val execution:Int=0,
    val unset:Int=0,
) {
}

data class GemTotalCount(val gemCount:Int=0)