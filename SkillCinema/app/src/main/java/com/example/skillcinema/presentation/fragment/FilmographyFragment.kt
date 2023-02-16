package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.data.GetPersonProfessionName
import com.example.skillcinema.data.PersonProfessionKeyList
import com.example.skillcinema.databinding.FragmentFilmographyBinding
import com.example.skillcinema.databinding.TabItemBinding
import com.example.skillcinema.entity.PersonMovie
import com.example.skillcinema.entity.PersonProfessionKey
import com.example.skillcinema.presentation.adapter.viewPager.VPAdapter
import com.example.skillcinema.presentation.fragment.view_pager.PersonMovieListFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class FilmographyFragment : Fragment() {

    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val filmographyVM = App.appComponent.filmographyVM()
    private val personProfessionKeyList = PersonProfessionKeyList().personProfessionKeyList
    private val getPersonProfessionName = GetPersonProfessionName()
    private val fragmentsList = mutableListOf<PersonMovieListFragment>()
    private val databaseViewModel = App.appComponent.databaseViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmographyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.pageName.init { activity?.onBackPressedDispatcher?.onBackPressed() }

        loadFilmography()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            loadFilmography()
        }

    }

    private fun loadFilmography() {

        val person = mainViewModel.person.value
        viewLifecycleOwner.lifecycleScope.launch {
            if (person != null) {
                binding.personName.text = person.nameRu ?: person.nameEn ?: "Неизвестно"
                filmographyVM.getFilmography(person)
            }
            val professionMap = filmographyVM.filmography.value
            fragmentsList.clear()
            if (professionMap != null && fragmentsList.isEmpty()) {
                binding.filmographyInformation.visibility = View.VISIBLE
                binding.noConnectionLayout.visibility = View.GONE
                fragmentsList.clear()
                personProfessionKeyList.forEach {
                    getSortedMovieList(it, professionMap)
                }
                if (fragmentsList.isNotEmpty()) {
                    val adapter = VPAdapter(requireActivity(), fragmentsList)
                    binding.filmography.adapter = adapter
                    TabLayoutMediator(binding.tabLayout, binding.filmography) { _, _ ->
                    }.attach()

                    for (i in 0 until fragmentsList.size) {
                        val tabItemBinding = TabItemBinding.inflate(layoutInflater)
                        tabItemBinding.number.text = fragmentsList[i].movieNumber.toString()
                        tabItemBinding.text.text = fragmentsList[i].name
                        binding.tabLayout.getTabAt(i)?.customView = tabItemBinding.root
                    }
                } else {
                    binding.filmographyInformation.visibility = View.GONE
                    binding.noConnectionLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getSortedMovieList(
        personProfessionKey: PersonProfessionKey,
        professionMap: Map<String, List<PersonMovie>>
    ) {
        val movieList = professionMap[personProfessionKey.key]
        if (movieList != null && movieList.isNotEmpty()) {
            val name = getPersonProfessionName.getPersonProfessionName(
                requireContext(),
                personProfessionKey.key
            )

            fragmentsList.add(
                PersonMovieListFragment.newInstance(
                    filmographyVM.getPagingMovies(movieList),
                    databaseViewModel.viewedMovies.value,
                    name,
                    movieList.size
                ) { findNavController().navigate(R.id.action_filmographyFragment_to_moviePageFragment) })
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}