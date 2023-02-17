package com.example.skillcinema.presentation.adapter.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.skillcinema.data.PersonProfessionKeyList
import com.example.skillcinema.entity.PersonMovie
import com.example.skillcinema.presentation.fragment.view_pager.PersonMovieListFragment

class FilmographyViewPagerAdapter(
    fm: FragmentManager, lifecycle: Lifecycle,
    private val professionMap: Map<String, List<PersonMovie>>?,
) : FragmentStateAdapter(fm, lifecycle) {

    private val personProfessionKeyList = PersonProfessionKeyList().personProfessionKeyList
    val fragmentList = mutableListOf<PersonMovieListFragment>()

    init {
        personProfessionKeyList.forEach {
            val movieList = professionMap?.get(it.key)
            if (movieList != null && movieList.isNotEmpty())
                fragmentList.add(PersonMovieListFragment.newInstance(
                    movieList,
                    it.key
                ))
        }
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}