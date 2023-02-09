package com.example.skillcinema.presentation.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.data.GetPersonProfessionName
import com.example.skillcinema.data.PersonProfessionKeyList
import com.example.skillcinema.databinding.DialogLoadingBinding
import com.example.skillcinema.databinding.FragmentFilmographyBinding
import com.example.skillcinema.databinding.TabItemBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.PersonProfessionKey
import com.example.skillcinema.presentation.fragment.VPFragment.PersonMovieListFragment
import com.example.skillcinema.presentation.viewmodel.adapter.viewPager.VPAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class FilmographyFragment : Fragment() {

    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val filmographyVM = App.appComponent.filmographyVM()
    private val personProfessionKeyList = PersonProfessionKeyList().personProfessionKeyList
    private val getPersonProfessionName = GetPersonProfessionName()
    private val fragmentsList = mutableListOf<PersonMovieListFragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmographyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.pageName.init { activity?.onBackPressedDispatcher?.onBackPressed() }

        loadFilmography()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            loadFilmography()
        }

    }

    private fun loadFilmography() {

        val dialog = Dialog(requireContext())
        val dialogBinding = DialogLoadingBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val person = mainViewModel.person.value
        viewLifecycleOwner.lifecycleScope.launch {
            binding.tabLayout.visibility = View.GONE
            dialog.show()
            if (person != null) {
                binding.personName.text = person.nameRu ?: person.nameEn ?: "Неизвестно"
                filmographyVM.getFilmography(person)
            }
            val professionMap = filmographyVM.filmography.value
            if (professionMap != null && fragmentsList.isEmpty()) {
                binding.filmographyInformation.visibility = View.VISIBLE
                binding.noConnectionLayout.visibility = View.GONE
                personProfessionKeyList.forEach {
                    getSortedMovieList(it, professionMap)
                }
                val activity = activity
                if (fragmentsList.isNotEmpty() && activity != null) {
                    val adapter = VPAdapter(activity, fragmentsList)
                    binding.filmography.adapter = adapter
                    TabLayoutMediator(binding.tabLayout, binding.filmography) { tabItem, position ->
                        tabItem.text = getString(
                            R.string.name_number,
                            fragmentsList[position].name,
                            fragmentsList[position].movieNumber
                        )
                    }.attach()
                    for (i in 0 until fragmentsList.size) {
                        val tabItemBinding = TabItemBinding.inflate(layoutInflater)
                        tabItemBinding.number.text = fragmentsList[i].movieNumber.toString()
                        tabItemBinding.text.text = fragmentsList[i].name
                        binding.tabLayout.getTabAt(i)?.customView = tabItemBinding.root
                    }
                } else {
                    binding.filmographyInformation.visibility = View.GONE
                    binding.noConnectionLayout.visibility = View.VISIBLE
                }
            }
            binding.tabLayout.visibility = View.VISIBLE
            dialog.dismiss()
        }
    }

    private fun getSortedMovieList(
        personProfessionKey: PersonProfessionKey,
        professionMap: Map<String, List<Movie>>
    ) {
        val movieList = professionMap[personProfessionKey.key]
        if (movieList != null && movieList.isNotEmpty()) {
            val name = getPersonProfessionName.getPersonProfessionName(
                requireContext(),
                personProfessionKey.key
            )
            fragmentsList.clear()
            fragmentsList.add(PersonMovieListFragment.newInstance(movieList, name, movieList.size))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}