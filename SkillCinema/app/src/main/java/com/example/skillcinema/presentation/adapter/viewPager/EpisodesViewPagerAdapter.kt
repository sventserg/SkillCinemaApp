package com.example.skillcinema.presentation.adapter.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.skillcinema.entity.Season
import com.example.skillcinema.presentation.fragment.view_pager.EpisodesFragment

class EpisodesViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private val seasons: List<Season>) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return seasons.size
    }

    override fun createFragment(position: Int): Fragment {
        return EpisodesFragment(seasons[position].episodes)
    }
}