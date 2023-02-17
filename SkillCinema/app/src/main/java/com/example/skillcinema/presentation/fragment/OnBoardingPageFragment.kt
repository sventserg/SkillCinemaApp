package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentOnBoardingPageBinding
import com.example.skillcinema.presentation.adapter.viewPager.ViewPagerFragmentAdapter
import com.example.skillcinema.presentation.fragment.view_pager.OnBoardingFragment


class OnBoardingPageFragment : Fragment() {

    private var _binding: FragmentOnBoardingPageBinding? = null
    private val binding get() = _binding!!
    private val fragmentList = mutableListOf<OnBoardingFragment>()
    private val databaseViewModel = App.appComponent.databaseViewModel()
    private val indicatorListener = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentIndicator(position)
        }
    }

    private fun start() {
        databaseViewModel.onBoardingIsOver()
        findNavController().navigate(R.id.action_onBoardingPageFragment_to_mainFragment2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentList.add(OnBoardingFragment.newInstance(R.drawable.on_boarding_image_1))
        fragmentList.add(OnBoardingFragment.newInstance(R.drawable.on_boarding_image_2))
        fragmentList.add(OnBoardingFragment.newInstance(R.drawable.on_boarding_image_3))
        currentIndicator(0)

        binding.firstIndicator.setOnClickListener {
            binding.viewPager.setCurrentItem(0, true)
        }
        binding.secondIndicator.setOnClickListener {
            binding.viewPager.setCurrentItem(1, true)
        }
        binding.thirdIndicator.setOnClickListener {
            binding.viewPager.setCurrentItem(2, true)
        }

        if (fragmentList.isNotEmpty()) {
            binding.viewPager.adapter = ViewPagerFragmentAdapter(
                childFragmentManager,
                viewLifecycleOwner.lifecycle,
                fragmentList
            )
            binding.viewPager.registerOnPageChangeCallback(indicatorListener)
        }
        binding.startButton.setOnClickListener { start() }
        binding.skip.setOnClickListener { start() }
    }

    private fun currentIndicator(position: Int) {
        when (position) {
            0 -> {
                binding.firstIndicator.setImageResource(R.drawable.on_boarding_selected_indicator)
                binding.secondIndicator.setImageResource(R.drawable.on_boarding_not_selected_indicator)
                binding.thirdIndicator.setImageResource(R.drawable.on_boarding_not_selected_indicator)
            }
            1 -> {
                binding.firstIndicator.setImageResource(R.drawable.on_boarding_not_selected_indicator)
                binding.secondIndicator.setImageResource(R.drawable.on_boarding_selected_indicator)
                binding.thirdIndicator.setImageResource(R.drawable.on_boarding_not_selected_indicator)
            }
            else -> {
                binding.firstIndicator.setImageResource(R.drawable.on_boarding_not_selected_indicator)
                binding.secondIndicator.setImageResource(R.drawable.on_boarding_not_selected_indicator)
                binding.thirdIndicator.setImageResource(R.drawable.on_boarding_selected_indicator)
            }
        }
    }


    override fun onDestroyView() {
        binding.viewPager.adapter = null
        binding.viewPager.unregisterOnPageChangeCallback(indicatorListener)
        fragmentList.clear()
        _binding = null
        super.onDestroyView()
    }
}
