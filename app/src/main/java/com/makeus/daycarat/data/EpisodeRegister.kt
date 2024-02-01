package com.makeus.daycarat.data

data class EpisodeRegister(var title:String ="" ,
                           var date:String ="" ,var activityTag:String ="" ,
                           var episodeContents:List<EpisodeContent> = listOf() )

data class EpisodeRegisterWithId(var episodeId:Int , var title:String ="" ,
                           var selectedDate:String ="" ,var activityTag:String ="" ,
                           var episodeContents:List<EpisodeContent> = listOf() )