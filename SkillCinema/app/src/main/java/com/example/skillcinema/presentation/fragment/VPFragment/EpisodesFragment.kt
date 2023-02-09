package com.example.skillcinema.presentation.fragment.VPFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillcinema.databinding.FragmentEpisodesBinding
import com.example.skillcinema.entity.Episode
import com.example.skillcinema.presentation.viewmodel.adapter.decorator.HorizontalItemDecoration
import com.example.skillcinema.presentation.viewmodel.adapter.decorator.SimpleVerticalItemDecoration
import com.example.skillcinema.presentation.viewmodel.adapter.episode.EpisodeAdapter

class EpisodesFragment(
    private val episodesList: List<Episode>
) : Fragment() {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EpisodeAdapter(episodesList, requireContext())
        binding.episodesContainer.adapter = adapter
        binding.episodesContainer.addItemDecoration(SimpleVerticalItemDecoration(40))
    }

    companion object {
        fun newInstance(episodesList: List<Episode>) = EpisodesFragment(episodesList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}