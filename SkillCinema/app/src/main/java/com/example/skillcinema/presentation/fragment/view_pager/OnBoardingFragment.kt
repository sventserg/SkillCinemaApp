package com.example.skillcinema.presentation.fragment.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skillcinema.databinding.ViewPagerFragmentOnBoardingBinding


class OnBoardingFragment(
    private val image: Int
) : Fragment() {

    private var _binding: ViewPagerFragmentOnBoardingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewPagerFragmentOnBoardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onBoardingImage.setImageResource(image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(
            image: Int
        ) = OnBoardingFragment(image)
    }
}