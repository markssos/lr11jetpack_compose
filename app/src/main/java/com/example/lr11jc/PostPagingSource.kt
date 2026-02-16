package com.example.lr11jc

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lr11jc.Post

class PostPagingSource(
    private val api: PostApi
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 1
            val response = api.getPosts(page = page, limit = params.loadSize)

            val nextKey = if (response.size == params.loadSize) {
                page + 1
            } else {
                null
            }

            val prevKey = if (page > 1) page - 1 else null

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {

            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}