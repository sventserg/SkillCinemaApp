package com.example.skillcinema.presentation.adapter.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.skillcinema.entity.MovieImageType
import com.example.skillcinema.presentation.fragment.view_pager.ImageListFragment

class ImageViewPagerAdapter(
    fm: FragmentManager, lifecycle: Lifecycle,
    private val typeList: List<MovieImageType>
) : FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return typeList.size
    }

    override fun createFragment(position: Int): Fragment {
        return ImageListFragment.newInstance(typeList[position])
    }

}
