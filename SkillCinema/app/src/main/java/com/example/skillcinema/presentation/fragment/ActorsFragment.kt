package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentActorsBinding
import com.example.skillcinema.entity.Staff
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.viewmodel.adapter.actor.ActorsAdapter
import com.example.skillcinema.presentation.viewmodel.adapter.decorator.SimpleVerticalItemDecoration


class ActorsFragment : Fragment() {

    private var _binding: FragmentActorsBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()

    private fun onCLickPerson(staffPerson: Staff) {
        mainViewModel.clickOnPerson(staffPerson)
        findNavController().navigate(R.id.action_actorsFragment_to_staffPersonPageFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActorsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spacing = (resources.displayMetrics.scaledDensity * DEFAULT_SPACING).toInt()
        binding.actors.addItemDecoration(SimpleVerticalItemDecoration(spacing))

        binding.pageName.init(mainViewModel.staffText.value) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        loadStaff()

        binding.swipeRefresh.setOnRefreshListener {
            loadStaff()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadStaff() {
        binding.actors.visibility = View.VISIBLE
        binding.noConnectionLayout.visibility = View.GONE
        val staff = mainViewModel.staff.value
        if (staff != null) {
            val adapter = ActorsAdapter(staff) { onCLickPerson(it) }
            binding.actors.adapter = adapter
        } else {
            binding.actors.visibility = View.GONE
            binding.noConnectionLayout.visibility = View.VISIBLE
        }
    }
}