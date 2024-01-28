package com.makeus.daycarat.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makeus.daycarat.hilt.EpisodeApi
import com.makeus.daycarat.hilt.GemApi


class GemContentPagingSource(private val retrofitInterface: GemApi,
                                       val keyword:String):
    PagingSource<Int, GemDetailConetent>() {

    override fun getRefreshKey(state: PagingState<Int, GemDetailConetent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?:state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int> ): LoadResult<Int, GemDetailConetent> {
        return try {
            val curKey = params.key ?: null
//            if (curKey != 1) delay(1_000L)
            val response = retrofitInterface.getGemKeywordList(keyword =keyword,  cursorId =  curKey, )
            LoadResult.Page(data= response.result?: listOf() ,
                prevKey = null,
                nextKey = if (response.result.isNullOrEmpty()) null
                else response.result.getOrNull(response.result.lastIndex)?.episodeId
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }


    }

}