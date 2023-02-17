package com.example.skillcinema.presentation.adapter.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TimePeriodViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}