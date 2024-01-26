package com.makeus.daycarat.data.paging

data class EpisodeDetailContent(
    var id :Int,
    val title:String,
    val date:String,
    val episodeState :String,
    val episodeKeyword :String?,
    val content :String
) {
}