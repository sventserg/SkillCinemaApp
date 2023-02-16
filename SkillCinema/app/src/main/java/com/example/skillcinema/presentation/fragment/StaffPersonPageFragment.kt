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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.DialogImageBinding
import com.example.skillcinema.databinding.DialogLoadingBinding
import com.example.skillcinema.databinding.FragmentStaffPersonPageBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.Person
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.MovieCollectionType
import com.example.skillcinema.presentation.decorator.HorizontalItemDecoration
import com.example.skillcinema.presentation.adapter.movieList.MovieListAdapter
import kotlinx.coroutines.launch

class StaffPersonPageFragment : Fragment() {

    private fun onClickMovie(movie: Movie) {
        mainViewModel.clickOnMovie(movie)
        findNavController().navigate(R.id.action_staffPersonPageFragment_to_moviePageFragment)
    }

    private fun clickOnFilmography(person: Person) {
        mainViewModel.setPerson(person)
        findNavController().navigate(R.id.action_staffPersonPageFragment_to_filmographyFragment)
    }

    private fun clickOnBestMovies(listName: String, movies: List<Movie>) {
        mainViewModel.setMovieListType(MovieCollectionType(movies, listName))
        findNavController().navigate(R.id.action_staffPersonPageFragment_to_moviesListPageFragment)
    }

    private var _binding: FragmentStaffPersonPageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val personVM = App.appComponent.staffPersonVM()

    private fun onPhotoClick(imageUrl: String?) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogImageBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.image.setOnClickListener { dialog.dismiss() }
        Glide
            .with(dialogBinding.root)
            .load(imageUrl)
            .fitCenter()
            .into(dialogBinding.image)
        dialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStaffPersonPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        val margin = (resources.displayMetrics.scaledDensity * START_END_MARGIN).toInt()
        val spacing = (resources.displayMetrics.scaledDensity * DEFAULT_SPACING).toInt()
        binding.bestMovies.addDecoration(HorizontalItemDecoration(margin, spacing))
        binding.backButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        loadStaffPersonInformation()
        startPostponedEnterTransition()
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            loadStaffPersonInformation()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun loadStaffPersonInformation() {

        val dialog = Dialog(requireContext())
        val dialogBinding = DialogLoadingBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        viewLifecycleOwner.lifecycleScope.launch {
            dialog.show()
            binding.staffPersonInformation.visibility = View.GONE
            binding.noConnectionLayout.visibility = View.GONE

            val selectedPerson = mainViewModel.selectedStaffPerson.value
            if (selectedPerson != null) {
                personVM.loadPerson(selectedPerson.staffId, requireContext())
            }
            val person = personVM.person.value
            if (person != null && person.personId == selectedPerson?.staffId) {
                Glide
                    .with(binding.root)
                    .load(person.posterUrl)
                    .centerCrop()
                    .into(binding.personPhoto)
                binding.filmographyTextLine.initTextLine(onForwardButtonClick = {
                    clickOnFilmography(person)
                })

                val personName = person.name()
                binding.personName.text = personName
                binding.personRole.text = person.profession
                binding.allMoviesText.text = personVM.moviesQuantity.value

                val bestMovies = personVM.bestPersonMovies.value
                val listName = getString(R.string.best_person_movies, personName)
                if (bestMovies != null && bestMovies.isNotEmpty()) {
                    binding.bestMovies.visibility = View.VISIBLE
                    val adapter =
                        MovieListAdapter(
                            movieList = bestMovies,
                            forwardArrow = bestMovies.size >= 10,
                            onClick = { onClickMovie(it) },
                            lastElementClick = { clickOnBestMovies(listName, bestMovies) })

                    binding.bestMovies.initView(
                        adapter = adapter,
                        onForwardButtonClick = { clickOnBestMovies(listName, bestMovies) }
                    )
                } else {
                    binding.bestMovies.visibility = View.GONE
                }
                binding.personPhoto.setOnClickListener { _ ->
                    onPhotoClick(person.posterUrl)
                }
                binding.staffPersonInformation.visibility = View.VISIBLE
            } else {
                binding.noConnectionLayout.visibility = View.VISIBLE
            }
            dialog.dismiss()
        }
    }
}