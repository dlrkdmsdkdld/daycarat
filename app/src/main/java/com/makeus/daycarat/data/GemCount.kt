package com.makeus.daycarat.data

data class GemCount(
    val communication:Int = 0,
    val conflictResolution:Int= 0,
    val passion:Int=0,
    val diligence:Int=0,
    val collaboration:Int=0,
    val leadership:Int=0,
    val feedback:Int=0,
    val unset:Int=0,
) {
}

data class GemTotalCount(val gemCount:Int=0)