package com.example.skillcinema.presentation.adapter.movieImage

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.domain.LoadMovieDataUseCase
import com.example.skillcinema.entity.MovieImage
import com.example.skillcinema.entity.MovieImageType

class MovieImagePagingSource(
    private val loadMovieData: LoadMovieDataUseCase,
    private val kinopoiskId: Int,
    private val type: MovieImageType
) : PagingSource<Int, MovieImage>() {

    override fun getRefreshKey(state: PagingState<Int, MovieImage>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieImage> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            loadMovieData.loadMovieImageForPaging(kinopoiskId, page, type)
        }.fold(
            onSuccess = {
                Log.d("Source", "Success ${it?.imageList?.size}")
                val data = it?.imageList
                if (data != null) {
                    LoadResult.Page(
                        data = data,
                        prevKey = null,
                        nextKey = if (data.isEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            },
            onFailure = {
                Log.d("Source", "Failure  $it")
                LoadResult.Error(it)
            }
        )
    }
}