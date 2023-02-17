package com.example.skillcinema.presentation.fragment.view_pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillcinema.databinding.ViewPagerFragmentEpisodesBinding
import com.example.skillcinema.entity.Episode
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.decorator.SimpleVerticalItemDecoration
import com.example.skillcinema.presentation.adapter.episode.EpisodeAdapter

class EpisodesFragment(
    private val episodesList: List<Episode>
) : Fragment() {

    private var _binding: ViewPagerFragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewPagerFragmentEpisodesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val margin = (resources.displayMetrics.scaledDensity * START_END_MARGIN).toInt()
        val spacing = (resources.displayMetrics.scaledDensity * DEFAULT_SPACING).toInt()

        val adapter = EpisodeAdapter(episodesList, requireContext())
        binding.episodesContainer.adapter = adapter
        binding.episodesContainer.addItemDecoration(SimpleVerticalItemDecoration(spacing, margin))
    }

    companion object {
        fun newInstance(episodesList: List<Episode>) = EpisodesFragment(episodesList)
    }

    override fun onDestroyView() {
//        binding.episodesContainer.adapter = null
        _binding = null
        super.onDestroyView()
    }
}