package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ChipBinding
import com.example.skillcinema.databinding.FragmentSeasonsBinding
import com.example.skillcinema.databinding.TabItemBinding
import com.example.skillcinema.presentation.fragment.VPFragment.EpisodesFragment
import com.example.skillcinema.presentation.viewmodel.adapter.viewPager.VPAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator


class SeasonsFragment : Fragment() {

    private var _binding: FragmentSeasonsBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val fragmentList = mutableListOf<EpisodesFragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeasonsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pageName.init(mainViewModel.selectedMovie.value?.name()) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        loadSeasonsInformation()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            loadSeasonsInformation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadSeasonsInformation() {
        val seasons = mainViewModel.seasons.value?.items
        binding.episodesNumber.text = mainViewModel.episodesNumber.value
        if (seasons != null) {
            binding.seasonsInformation.visibility = View.VISIBLE
            binding.noConnectionLayout.visibility = View.GONE
            fragmentList.clear()
            seasons.forEach {
                fragmentList.add(EpisodesFragment.newInstance(it.episodes))
            }
            val activity = activity
            if (fragmentList.isNotEmpty() && activity != null) {

                val adapter = VPAdapter(activity, fragmentList)
                binding.episodesContainer.adapter = adapter
                TabLayoutMediator(
                    binding.tabLayout,
                    binding.episodesContainer
                ) { tabItem, position ->
                    tabItem.text = (position+1).toString()
                }.attach()
                for (i in 0 until fragmentList.size) {
                    val tabItemBinding = TabItemBinding.inflate(layoutInflater)
                    tabItemBinding.text.text =
                        resources.getString(R.string.number_of_season, seasons[i].number)
                    tabItemBinding.number.visibility = View.GONE
                    binding.tabLayout.getTabAt(i)?.customView = tabItemBinding.root
                }
            }
        } else {
            binding.seasonsInformation.visibility = View.GONE
            binding.noConnectionLayout.visibility = View.VISIBLE
        }
    }
}