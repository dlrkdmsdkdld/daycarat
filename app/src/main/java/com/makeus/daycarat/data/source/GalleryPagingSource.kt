package com.makeus.daycarat.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makeus.daycarat.data.data.GalleryImage
import com.makeus.daycarat.domain.repository.GalleryRepository

class GalleryPagingSource(
    private val imageRepository: GalleryRepository,
    private val currnetLocation: String?,
) : PagingSource<Int, GalleryImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryImage> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val data = imageRepository.getAllPhotos(
                page = position,
                loadSize = params.loadSize,
                currentLocation = currnetLocation,
            )
            val endOfPaginationReached = data.isEmpty()
            val prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1
            val nextKey =
                if (endOfPaginationReached) null else position + (params.loadSize / PAGING_SIZE)
            LoadResult.Page(data, prevKey, nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryImage>): Int? {
        return state.anchorPosition?.let { achorPosition ->
            state.closestPageToPosition(achorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(achorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val PAGING_SIZE = 28
    }
}