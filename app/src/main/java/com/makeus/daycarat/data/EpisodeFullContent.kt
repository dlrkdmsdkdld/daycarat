package com.makeus.daycarat.data

data class EpisodeFullContent(
    var episodeId :Int,
    val title:String,
    val activityTagName:String,
    val selectedDate:String,
    val episodeState:String,
    val episodeContents:List<EpisodeContent>,

) {
}