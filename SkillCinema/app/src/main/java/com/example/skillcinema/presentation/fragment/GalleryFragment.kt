package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skillcinema.App
import com.example.skillcinema.data.GetMovieImageTypeRuName
import com.example.skillcinema.data.MovieImageTypeList
import com.example.skillcinema.databinding.FragmentGalleryBinding
import com.example.skillcinema.databinding.TabItemBinding
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.presentation.adapter.viewPager.ImageViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val galleryVM = App.appComponent.galleryVM()
    private val getMovieImageTypeName = GetMovieImageTypeRuName()
    private var mediator: TabLayoutMediator? = null
    private var adapter: ImageViewPagerAdapter? = null
    private val movieImageTypeList = MovieImageTypeList().movieImageTypeList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.pageName.init { activity?.onBackPressedDispatcher?.onBackPressed() }
        val movie = mainViewModel.selectedMovie.value
        val galleryTypeNumbers = mainViewModel.galleryTypeNumbers.value
        val imageNumbers = mutableListOf<Int>()
        val typeList = mutableListOf<MovieImageType>()
        movieImageTypeList.forEach {
            val imageNumber = galleryTypeNumbers[it.type]
            if (imageNumber != null && imageNumber > 0) {
                imageNumbers.add(imageNumber)
                typeList.add(it)
            }
        }

        if (movie != null) {
            val movieID = movie.id()
            galleryVM.setCurrentMovie(movieID)
            adapter = ImageViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, typeList)
            binding.gallery.adapter = adapter
            mediator = TabLayoutMediator(binding.tabLayout, binding.gallery) { _, _ ->
            }
            mediator?.attach()
            if (adapter != null) {
                for (i in 0 until typeList.size) {
                    val tabItemBinding = TabItemBinding.inflate(layoutInflater)
                    tabItemBinding.number.text =
                        imageNumbers[i].toString()
                    tabItemBinding.text.text = getMovieImageTypeName.getMovieImageTypeRuName(
                        requireContext(),
                        typeList[i]
                    )
                    binding.tabLayout.getTabAt(i)?.customView = tabItemBinding.root
                }
            }
        }
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.gallery.adapter = null
        adapter = null
        _binding = null
        super.onDestroyView()
    }
}