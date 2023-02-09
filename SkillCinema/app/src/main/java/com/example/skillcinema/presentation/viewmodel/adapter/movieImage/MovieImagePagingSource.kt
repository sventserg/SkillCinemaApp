package com.example.skillcinema.presentation.viewmodel.adapter.movieImage

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.data.*
import com.example.skillcinema.domain.LoadMovieData
import com.example.skillcinema.domain.LoadMovieImage
import com.example.skillcinema.entity.MovieImage
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.presentation.*

class MovieImagePagingSource(
    private val loadMovieImage: LoadMovieImage,
    private val kinopoiskId: Int,
    private val type: MovieImageType
) : PagingSource<Int, MovieImage>() {

    override fun getRefreshKey(state: PagingState<Int, MovieImage>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieImage> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            loadMovieImage.loadTypedImages(kinopoiskId, page, type)
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
                Log.d("Source", "Failure + $it")
                LoadResult.Error(it)
            }
        )
    }
}