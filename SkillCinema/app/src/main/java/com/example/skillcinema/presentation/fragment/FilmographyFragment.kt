package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.skillcinema.App
import com.example.skillcinema.data.GetPersonProfessionName
import com.example.skillcinema.databinding.FragmentFilmographyBinding
import com.example.skillcinema.databinding.TabItemBinding
import com.example.skillcinema.presentation.adapter.viewPager.FilmographyViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class FilmographyFragment : Fragment() {

    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val filmographyVM = App.appComponent.filmographyVM()
    private val getPersonProfessionName = GetPersonProfessionName()
    private var mediator: TabLayoutMediator? = null
    private var adapter: FilmographyViewPagerAdapter? = null

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
                binding.personName.text = person.name()
                filmographyVM.getFilmography(person)
            }
            val professionMap = filmographyVM.filmography.value

            adapter = FilmographyViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, professionMap)
            binding.filmography.adapter = adapter
            mediator = TabLayoutMediator(binding.tabLayout, binding.filmography) { _, _ ->

            }
            mediator?.attach()
            if (adapter != null) {
                for (i in 0 until adapter!!.fragmentList.size) {
                    val tabItemBinding = TabItemBinding.inflate(layoutInflater)
                    tabItemBinding.number.text = adapter!!.fragmentList[i].movieNumber.toString()
                    tabItemBinding.text.text = getPersonProfessionName.getPersonProfessionName(
                        requireContext(),
                        adapter!!.fragmentList[i].professionKey
                    )
                    binding.tabLayout.getTabAt(i)?.customView = tabItemBinding.root
                }
            }

        }
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.filmography.adapter = null
        adapter?.fragmentList?.clear()
        adapter = null
        _binding = null
        super.onDestroyView()
    }
}