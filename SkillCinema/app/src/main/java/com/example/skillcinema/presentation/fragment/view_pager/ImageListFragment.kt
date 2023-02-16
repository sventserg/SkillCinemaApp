package com.example.skillcinema.presentation.fragment.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import com.example.skillcinema.App
import com.example.skillcinema.databinding.ViewPagerFragmentImageListBinding
import com.example.skillcinema.entity.MovieImage
import com.example.skillcinema.presentation.adapter.movieImage.PagingMovieImageAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ImageListFragment(
    private val pagingImages: Flow<PagingData<MovieImage>>,
    val name: String,
    val imagesNumber: Int,
    val navigate: () -> Unit
) : Fragment() {

    private var _binding: ViewPagerFragmentImageListBinding? = null
    private val binding get() = _binding!!
    private val galleryVM = App.appComponent.galleryVM()

    private var adapter = PagingMovieImageAdapter {
        onImageClick(it, pagingImages)
    }

    private fun onImageClick(image: MovieImage?, pagedImages: Flow<PagingData<MovieImage>>) {
        galleryVM.setImage(image)
        galleryVM.setCurrentPagedImages(pagedImages)
        galleryVM.setCurrentListName(name)
        navigate()
    }

    private fun refreshAdapter() {
        this.adapter.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewPagerFragmentImageListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.galleryContainer.adapter = adapter

        binding.noConnectionButton.setOnClickListener {
            refreshAdapter()
        }

        pagingImages.onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.loadStateFlow.onEach {
            if (it.refresh != LoadState.Loading) {
                if (adapter.snapshot().isEmpty()) {
                    binding.noConnectionLayout.visibility = View.VISIBLE
                } else binding.noConnectionLayout.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun onDestroyView() {
        binding.galleryContainer.adapter = null
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(
            pagingImages: Flow<PagingData<MovieImage>>,
            name: String,
            imagesNumber: Int,
            navigate: () -> Unit
        ) = ImageListFragment(pagingImages, name, imagesNumber, navigate)
    }
}