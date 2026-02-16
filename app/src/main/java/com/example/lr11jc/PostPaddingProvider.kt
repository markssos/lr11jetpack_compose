package com.example.lr11jc

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.lr11jc.Post
import kotlinx.coroutines.flow.Flow

object PostPagingProvider {

    private val api = RetrofitClient.postApi

    fun getPostsFlow(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { PostPagingSource(api) }
        ).flow
    }
}