package com.example.data.repository.pagingdata

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.service.KudaGoService
import com.example.domain.models.PosterPresentation

class PosterPagingData(
    private val service: KudaGoService,
    private val errorText: (String) -> Unit,
) : PagingSource<Int, PosterPresentation>() {
    override fun getRefreshKey(state: PagingState<Int, PosterPresentation>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PosterPresentation> {
        val page: Int = params.key ?: 0
        val loadSize = params.loadSize
        val maxPage = 1000 / loadSize

        val userRepositoryList = mutableListOf<PosterPresentation>()

        try {
            val posters =
                service.getEvents()
        } catch (e: Exception) {
            errorText("ERROR:${e.message}")
        }

        val nextKey = if (userRepositoryList.size < loadSize && page < maxPage) null else page + 1

        val prevKey = if (page == 0) null else page - 1

        return LoadResult.Page(userRepositoryList, prevKey, nextKey)
    }
}
