package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.data.GetMovieImageTypeRuName
import com.example.skillcinema.data.MovieImageTypeList
import com.example.skillcinema.databinding.FragmentGalleryBinding
import com.example.skillcinema.databinding.TabItemBinding
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.presentation.fragment.view_pager.ImageListFragment
import com.example.skillcinema.presentation.adapter.viewPager.ImageVPAdapter
import com.google.android.material.tabs.TabLayoutMediator


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val galleryVM = App.appComponent.galleryVM()
    private val movieImageTypeList = MovieImageTypeList().movieImageTypeList
    private val fragmentList = mutableListOf<ImageListFragment>()

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
        if (movie != null) {
            val movieID = movie.id()
            if (fragmentList.isEmpty()) {
                movieImageTypeList.forEach {
                    getTabItem(it, movieID, galleryTypeNumbers, fragmentList)
                }
            }

            if (fragmentList.isNotEmpty()) {
                val adapter = ImageVPAdapter(this, fragmentList)
                binding.gallery.adapter = adapter
                TabLayoutMediator(binding.tabLayout, binding.gallery) { _, _ ->
                }.attach()
                for (i in 0 until fragmentList.size) {
                    val tabItemBinding = TabItemBinding.inflate(layoutInflater)
                    tabItemBinding.number.text =
                        fragmentList[i].imagesNumber.toString()
                    tabItemBinding.text.text = fragmentList[i].name
                    binding.tabLayout.getTabAt(i)?.customView = tabItemBinding.root
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.gallery.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun getTabItem(
        movieImageType: MovieImageType,
        movieID: Int,
        galleryTypeNumbers: Map<String, Int>,
        fragmentList: MutableList<ImageListFragment>
    ) {
        val getMovieImageTypeName = GetMovieImageTypeRuName()
        val imageNumbers = galleryTypeNumbers[movieImageType.type]
        if (imageNumbers != null && imageNumbers > 0) {
            fragmentList.add(
                ImageListFragment.newInstance(
                    galleryVM.pagedImages(movieID, movieImageType),
                    getMovieImageTypeName.getMovieImageTypeRuName(requireContext(), movieImageType),
                    imageNumbers
                ) {
                    findNavController().navigate(R.id.action_galleryFragment_to_imageScrollFragment)
                }
            )
        }
    }
}