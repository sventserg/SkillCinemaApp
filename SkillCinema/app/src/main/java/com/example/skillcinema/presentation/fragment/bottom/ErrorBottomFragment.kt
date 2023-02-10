package com.example.skillcinema.presentation.fragment.bottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillcinema.R
import com.example.skillcinema.databinding.BottomFragmentErrorBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ErrorBottomFragment(
    val description: String
) : BottomSheetDialogFragment() {

    private var _binding: BottomFragmentErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomFragmentErrorBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.errorDescription.text = description
        binding.closeButton.setOnClickListener { this.dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}