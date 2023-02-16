package com.example.skillcinema.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.FragmentSearchFilterBinding
import com.example.skillcinema.presentation.SettingsFilter
import com.example.skillcinema.presentation.decorator.HorizontalDividerDecoration
import com.example.skillcinema.presentation.adapter.filter.MovieFilterAdapter


class SearchFilterFragment : Fragment() {

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!
    private val searchPageVM = App.appComponent.searchPageVM()

    private fun onClickFilter(filterResult: String) {
        when (searchPageVM.settingsFilter.value) {
            is SettingsFilter.COUNTRY -> searchPageVM.setCountry(filterResult)
            is SettingsFilter.GENRE -> searchPageVM.setGenre(filterResult)
            else -> {}
        }
        findNavController().navigate(R.id.action_searchFilterFragment_to_searchSettingsFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filter = searchPageVM.settingsFilter.value
        if (filter != null) {
            binding.pageName.init(getString(filter.filterName)) {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            binding.inputText.hint = getString(filter.hint)
            val filterList = mutableListOf<String>()
            filter.filterList.forEach {
                filterList.add(it.name)
            }
            val adapter = MovieFilterAdapter(filterList) { onClickFilter(it) }
            binding.results.adapter = adapter
            val drawable = ResourcesCompat.getDrawable(resources, R.drawable.horizontal_divider, null)
            if (drawable != null) {
                binding.results.addItemDecoration(HorizontalDividerDecoration(drawable))
            }

            binding.inputText.addTextChangedListener {
                val inputText = binding.inputText.text.toString()
                val newList = mutableListOf<String>()
                filterList.forEach {
                    val match = Regex(inputText, RegexOption.IGNORE_CASE).find(it)
                    if (match != null) newList.add(it)
                }
                val newAdapter = MovieFilterAdapter(newList) { onClickFilter(it) }
                binding.results.adapter = newAdapter
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}