package com.example.skillcinema.presentation.adapter.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImageVPAdapter(
    fragment: Fragment,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}
