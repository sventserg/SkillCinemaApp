package com.example.skillcinema.presentation.fragment.view_pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillcinema.databinding.ViewPagerFragmentScrollableImageBinding

class ScrollableImageFragment : Fragment() {

    private var _binding: ViewPagerFragmentScrollableImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewPagerFragmentScrollableImageBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}