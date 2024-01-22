package com.makeus.daycarat.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makeus.daycarat.hilt.EpisodeApi
import kotlinx.coroutines.delay


class EpisodeContentByDatePagingSource(private val retrofitInterface: EpisodeApi , val month:Int , val year:Int):
    PagingSource<Int, EpisodeDetailContent>() { //페이징에 대한 클래스 페이징이란 사용자가 화면을 내릴 때 마다 적절하게 화면을 보여주기위한 기술
    override fun getRefreshKey(state: PagingState<Int, EpisodeDetailContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?:state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int> ): LoadResult<Int, EpisodeDetailContent> {
        return try {
            val curKey = params.key ?: null
            if (curKey != 1) delay(1_000L)
            val response = retrofitInterface.getEpisodeOderByDate(year = year, month = month ,  cursorId =  curKey, )

            LoadResult.Page(data= response.result?: listOf() ,
                prevKey = if(curKey == 1) null else { if (response.result?.isNullOrEmpty() == true) null else response.result?.firstOrNull()?.id }, //이전 페이지에 대한 정보
                nextKey = if (response.result?.isNullOrEmpty() == true) null else response.result?.getOrNull(response.result.lastIndex)?.id
            )


        }catch (e:Exception){
            LoadResult.Error(e)
        }


    }

//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDetailContent> {
//
//    }
}