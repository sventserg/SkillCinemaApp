package com.example.skillcinema.presentation.fragment.VPFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import com.example.skillcinema.App
import com.example.skillcinema.databinding.FragmentImageListBinding
import com.example.skillcinema.domain.LoadMovieImage
import com.example.skillcinema.entity.MovieImage
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.presentation.viewmodel.adapter.movieImage.MovieImagePagingSource
import com.example.skillcinema.presentation.viewmodel.adapter.movieImage.PagingMovieImageAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ImageListFragment(
    private val kinopoiskID: Int,
    private val type: MovieImageType,
    private val loadMovieImage: LoadMovieImage,
    val name: String,
    val imagesNumber: Int,
    val navigate: () -> Unit
) : Fragment() {

    private val pagingConfig = PagingConfig(
        pageSize = 10,
        enablePlaceholders = true
    )
    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!
    private val galleryVM = App.appComponent.galleryVM()
    private lateinit var pagedImages: Flow<PagingData<MovieImage>>

    private fun onImageClick(image: MovieImage?) {
        galleryVM.setImage(image)
        galleryVM.setCurrentPagedImages(pagedImages)
        navigate()
    }

    private val adapter = PagingMovieImageAdapter {
        onImageClick(it)
    }

    fun refresh() {
        adapter.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageListBinding.inflate(inflater)
        pagedImages = Pager(
            config = pagingConfig,
            initialKey = null,
            pagingSourceFactory = {
                MovieImagePagingSource(
                    loadMovieImage = loadMovieImage,
                    kinopoiskId = kinopoiskID,
                    type = type
                )
            }
        ).flow.cachedIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.galleryContainer.adapter = adapter

        binding.noConnectionButton.setOnClickListener {
            adapter.refresh()
        }

        pagedImages.onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.galleryContainer.scrollToPosition(0)

        adapter.loadStateFlow.onEach {
            if (it.refresh != LoadState.Loading) {
                if (adapter.snapshot().isEmpty()) {
                    binding.noConnectionLayout.visibility = View.VISIBLE
                } else binding.noConnectionLayout.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(
            kinopoiskID: Int,
            type: MovieImageType,
            loadMovieImage: LoadMovieImage,
            name: String,
            imagesNumber: Int,
            navigate: () -> Unit
        ) = ImageListFragment(kinopoiskID, type, loadMovieImage, name, imagesNumber, navigate)
    }
}