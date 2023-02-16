package com.example.skillcinema.presentation.fragment

import android.animation.LayoutTransition
import android.animation.LayoutTransition.TransitionListener
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.skillcinema.App
import com.example.skillcinema.R
import com.example.skillcinema.databinding.DialogImageBinding
import com.example.skillcinema.databinding.DialogLoadingBinding
import com.example.skillcinema.databinding.FragmentMoviePageBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.entity.MovieImage
import com.example.skillcinema.entity.Staff
import com.example.skillcinema.presentation.AppBarStateChangeListener
import com.example.skillcinema.presentation.DEFAULT_SPACING
import com.example.skillcinema.presentation.START_END_MARGIN
import com.example.skillcinema.presentation.fragment.bottom.AddMovieToCollectionFragment
import com.example.skillcinema.presentation.MovieCollectionType
import com.example.skillcinema.presentation.decorator.HorizontalItemDecoration
import com.example.skillcinema.presentation.adapter.movieImage.MovieImageAdapter
import com.example.skillcinema.presentation.adapter.movieList.MovieListAdapter
import com.example.skillcinema.presentation.adapter.staff.StaffAdapter
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.abs

class MoviePageFragment : Fragment() {

    private var _binding: FragmentMoviePageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel = App.appComponent.mainViewModel()
    private val moviePageVM = App.appComponent.moviePageVM()
    private val databaseViewModel = App.appComponent.databaseViewModel()
    private val appBarStateChangeListener = createAppBarStateChangeListener()
    private var isDescriptionCollapsed = true
    private var currentAnimationState = IDLE_ANIMATION_STATE
    private fun isRunning(): Boolean {
        return currentAnimationState != IDLE_ANIMATION_STATE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        _binding = FragmentMoviePageBinding.inflate(inflater)
        applyLayoutTransition()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        postponeEnterTransition()
        val margin = (resources.displayMetrics.scaledDensity * START_END_MARGIN).toInt()
        val spacing = (resources.displayMetrics.scaledDensity * DEFAULT_SPACING).toInt()

        addDecorations(margin, spacing)

        binding.appBarLayout.addOnOffsetChangedListener(appBarStateChangeListener)
        binding.toolbar.title = mainViewModel.selectedMovie.value?.name()

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.TRANSPARENT

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.selectedMovie.collect {
                loadMovie(it)
            }
        }

        binding.noConnectionButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                loadMovie(mainViewModel.selectedMovie.value)
            }
        }


        // Is movie added to favorites
        viewLifecycleOwner.lifecycleScope.launch {
            databaseViewModel.isFavorite.collect {
                if (it) binding.favoriteIcon.setImageResource(R.drawable.mp_favorite_icon)
                else binding.favoriteIcon.setImageResource(R.drawable.heart_off)
            }
        }

        // Is movie added to want to watch list
        viewLifecycleOwner.lifecycleScope.launch {
            databaseViewModel.isWantToWatch.collect {
                if (it) binding.wantToWatchIcon.setImageResource(R.drawable.ic_baseline_turned_in_24)
                else binding.wantToWatchIcon.setImageResource(R.drawable.ic_baseline_turned_in_not_24)
            }
        }

        // Is movie added to viewed list
        viewLifecycleOwner.lifecycleScope.launch {
            databaseViewModel.isViewed.collect {
                if (it) binding.notWatchedIcon.setImageResource(R.drawable.mp_watched_icon)
                else binding.notWatchedIcon.setImageResource(R.drawable.mp_not_watched_icon)
            }
        }

        //Back button behavior
        binding.actionToolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        //Description behaviour
        binding.description.maxLines = DESCRIPTION_MAX_LINES
        binding.description.setOnClickListener {
            if (isRunning()) {
                binding.container.layoutTransition = binding.container.layoutTransition
            }
            if (isDescriptionCollapsed) {
                currentAnimationState = EXPANDING_ANIMATION_STATE
                binding.description.maxLines = Int.MAX_VALUE
                isDescriptionCollapsed = false
            } else {
                currentAnimationState = COLLAPSING_ANIMATION_STATE
                binding.description.maxLines = DESCRIPTION_MAX_LINES
                isDescriptionCollapsed = true
            }
        }

    }

    override fun onDestroyView() {
        binding.appBarLayout.removeOnOffsetChangedListener(appBarStateChangeListener)
        _binding = null
        super.onDestroyView()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = resources.getColor(R.color.primaryDarkColor, null)
    }

    private fun onClickMovie(movie: Movie) {
        mainViewModel.clickOnMovie(movie)
    }

    private fun onCLickPerson(staffPerson: Staff) {
        mainViewModel.clickOnPerson(staffPerson)
        findNavController().navigate(R.id.action_moviePageFragment_to_staffPersonPageFragment)
    }

    private fun onClickSimilarMovies(movieList: List<Movie>) {
        mainViewModel.setMovieListType(
            MovieCollectionType(
                movieList,
                resources.getString(R.string.similar_movies)
            )
        )
    }

    private fun onGalleryImageClick(image: MovieImage) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogImageBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.image.setOnClickListener { dialog.dismiss() }
        Glide
            .with(dialogBinding.root)
            .load(image.imageUrl)
            .fitCenter()
            .into(dialogBinding.image)
        dialog.show()
    }

    private fun getFirstNStaffPerson(staff: List<Staff>, n: Int): List<Staff> {
        val staffList = mutableListOf<Staff>()
        val size = kotlin.math.min(staff.size, n)
        var i = 0
        while (i < size) {
            staffList.add(staff[i])
            i++
        }
        return staffList
    }

    private fun applyLayoutTransition() {
        val transition = LayoutTransition()
        transition.setDuration(300)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        transition.addTransitionListener(object : TransitionListener {
            override fun startTransition(
                p0: LayoutTransition?,
                p1: ViewGroup?,
                p2: View?,
                p3: Int
            ) {
            }

            override fun endTransition(p0: LayoutTransition?, p1: ViewGroup?, p2: View?, p3: Int) {
                currentAnimationState = IDLE_ANIMATION_STATE
            }
        })
        binding.container.layoutTransition = transition
    }

    private suspend fun loadMovie(movie: Movie?) {

        this.postponeEnterTransition()
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogLoadingBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        dialog.show()
        binding.mainContainer.visibility = View.INVISIBLE
        if (!isDescriptionCollapsed) {
            binding.description.maxLines = DESCRIPTION_MAX_LINES
            isDescriptionCollapsed = true
        }

        databaseViewModel.updateCollections()

        val movieID = movie?.id()
        if (movieID != null) {
            // Set selected movie to view model and load movie information
            moviePageVM.setMovie(movieID)
            Log.d("Fragment", "movieID: $movieID")
        }
        val selectedMovie = moviePageVM.selectedMovie.value
        if (selectedMovie != null) {
            loadMovieInformation(selectedMovie)
            databaseViewModel.isMovieInCollection(selectedMovie.id())
            isItSeries()
            getActors()
            getWorkers()
            getImages()
            getSimilarMovies()
            binding.noConnectionLayout.visibility = View.GONE
            binding.container.visibility = View.VISIBLE
            binding.buttons.visibility = View.VISIBLE
        } else {
            noMovieInformation()
            binding.noConnectionLayout.visibility = View.VISIBLE
            binding.container.visibility = View.GONE
            binding.buttons.visibility = View.GONE
        }
        binding.appBarLayout.setExpanded(true, true)
        delay(1000)
        binding.mainContainer.visibility = View.VISIBLE
        this.startPostponedEnterTransition()
        dialog.dismiss()
    }

    private fun addDecorations(margin: Int, spacing: Int) {
        binding.actors.addItemDecoration(
            HorizontalItemDecoration(
                margin,
                spacing,
                ACTORS_LINE_COUNT,
                spacing
            )
        )
        binding.workers.addItemDecoration(
            HorizontalItemDecoration(
                margin,
                spacing,
                WORKERS_LINE_COUNT,
                spacing
            )
        )
        binding.gallery.addItemDecoration(HorizontalItemDecoration(margin, spacing))
        binding.similarMovies.addItemDecoration(
            HorizontalItemDecoration(
                margin,
                spacing
            )
        )
    }

    private fun loadMovieInformation(movie: Movie) {

        binding.movieInformation.visibility = View.VISIBLE
        binding.moviePoster.setPadding(0, 0, 0, 0)

        // Save movie to interested movies
        databaseViewModel.saveMovieToInterested(movie, Date().time)

        val movieID = movie.id()

        //Get movie logo and cover
        val cover = movie.coverUrl ?: movie.posterUrl
        val logo = movie.logoUrl
        Glide
            .with(requireContext())
            .load(cover)
            .into(binding.moviePoster)
        Glide
            .with(requireContext())
            .load(logo)
            .into(binding.movieLogo)

        //Load movie information
        binding.movieName.text = movie.name()
        binding.kinopoiskRating.text = movie.descriptionThirdLine()
        binding.kinopoiskRating.text = movie.rating()
        if (movie.description != null) {
            binding.description.text = movie.description
        } else binding.description.visibility = View.GONE
        if (movie.shortDescription != null) {
            binding.shortDescription.text = movie.shortDescription
        } else binding.shortDescription.visibility = View.GONE
        binding.descriptionSecondLine.text = movie.descriptionSecondLine()
        binding.descriptionThirdLine.text = movie.descriptionThirdLine()
        if (movie.slogan != null) {
            binding.slogan.text = movie.slogan
        } else binding.slogan.visibility = View.GONE

        //Add movie to favorites
        binding.favoriteIcon.setOnClickListener {
            if (databaseViewModel.isFavorite.value) {
                databaseViewModel.deleteMovieFromFavorites(movieID)
            } else {
                databaseViewModel.saveMovieToFavorite(movie, Date().time)
            }
        }

        //Add movie to want to watch list
        binding.wantToWatchIcon.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (databaseViewModel.isWantToWatch.value) {
                    databaseViewModel.deleteMovieFromWantToWatchMovies(movieID)
                } else {
                    databaseViewModel.saveMovieToWantToWatch(movie, Date().time)
                }
            }
        }

        //Add movie to viewed list
        binding.notWatchedIcon.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (databaseViewModel.isViewed.value) {
                    databaseViewModel.deleteMovieFromViewedMovies(movieID)
                } else {
                    databaseViewModel.saveMovieToViewed(movie, Date().time)
                }
            }
        }

        //Add movie to collection
        binding.addMovieToCollectionIcon.setOnClickListener {
            val dialog =
                AddMovieToCollectionFragment(
                    movie,
                    databaseViewModel
                )
            val sfm = activity?.supportFragmentManager
            if (sfm != null) {
                dialog.show(sfm, "Dialog")
            }
        }

        //Share button behaviour
        binding.shareIcon.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${movie.webUrl}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun noMovieInformation() {
        binding.moviePoster.setImageResource(R.drawable.wifi_off_icon)
        val padding = (resources.displayMetrics.scaledDensity * PADDING).toInt()
        binding.moviePoster.setPadding(padding, padding, padding, padding)
        binding.movieInformation.visibility = View.GONE
    }

    //Check is it series
    private fun isItSeries() {
        if (moviePageVM.isItSeries.value) {
            val seasons = moviePageVM.seasons.value
            val episodesNumber = moviePageVM.episodesNumber.value
            if (seasons != null && episodesNumber != null) {

                //Set series data to MainViewModel
                mainViewModel.setSeasons(seasons)
                mainViewModel.setEpisodesNumber(episodesNumber)
            }

            //Forward to seasons fragment
            binding.serialInformation.initTextLine {
                findNavController().navigate(R.id.action_moviePageFragment_to_seasonsFragment)
            }
            binding.episodesText.text = moviePageVM.episodesNumber.value
        } else {
            binding.serialInformation.visibility = View.GONE
            binding.episodesText.visibility = View.GONE
        }
    }

    //Get actors information
    private fun getActors() {
        val actors = moviePageVM.actors.value
        if (actors == null || actors.isEmpty()) {
            binding.actorsInformation.visibility = View.GONE
        } else {
            val first20actors = getFirstNStaffPerson(actors, ACTORS_NUMBER)
            when (first20actors.size) {
                1 -> binding.actors.layoutManager = getLayoutManager(1)
                2 -> binding.actors.layoutManager = getLayoutManager(2)
                3 -> binding.actors.layoutManager = getLayoutManager(3)
                else -> binding.actors.layoutManager = getLayoutManager(4)
            }
            val actorsAdapter = StaffAdapter(first20actors) { staff ->
                onCLickPerson(staff)
            }
            binding.actors.adapter = actorsAdapter

            binding.actorsInformation.initTextLine(forwardText = actors.size.toString()) {
                //Navigate to staff page with actors
                mainViewModel.setStaff(actors, getString(R.string.movie_actors))
                findNavController().navigate(R.id.action_moviePageFragment_to_actorsFragment)
            }
        }
    }

    //Get workers information
    private fun getWorkers() {
        val workers = moviePageVM.workers.value
        if (workers == null || workers.isEmpty()) {
            binding.workersInformation.visibility = View.GONE
        } else {
            val first6workers = getFirstNStaffPerson(workers, WORKERS_NUMBER)
            when (first6workers.size) {
                1 -> binding.workers.layoutManager = getLayoutManager(1)
                else -> binding.workers.layoutManager = getLayoutManager(2)
            }
            val workersAdapter = StaffAdapter(first6workers) { staff ->
                onCLickPerson(staff)
            }
            binding.workers.adapter = workersAdapter

            binding.workersInformation.initTextLine(
                forwardText = workers.size.toString()
            ) {
                //Navigate to staff page with workers
                mainViewModel.setStaff(workers, getString(R.string.movie_workers))
                findNavController().navigate(R.id.action_moviePageFragment_to_actorsFragment)
            }
        }
    }

    //Get movie images
    private fun getImages() {
        val gallery = moviePageVM.gallery.value
        if (gallery == null || gallery.isEmpty()) {
            binding.galleryInformation.visibility = View.GONE
        } else {
            val adapter = MovieImageAdapter(gallery) {
                onGalleryImageClick(it)
            }
            binding.gallery.adapter = adapter
        }

        //Set image numbers to MainViewModel
        mainViewModel.setGalleryTypeNumbers(moviePageVM.galleryTypeNumbers.value)

        binding.galleryInformation.initTextLine(
            forwardText = moviePageVM.gallerySize.value.toString()
        ) {
            //Navigate to gallery page
            findNavController().navigate(R.id.action_moviePageFragment_to_galleryFragment)
        }
    }

    //Get similar movies
    private fun getSimilarMovies() {
        val similarMovies = moviePageVM.similarMovies.value
        if (similarMovies == null || similarMovies.isEmpty()) {
            binding.similarMoviesInformation.visibility = View.GONE
        } else {
            val similarMoviesAdapter =
                MovieListAdapter(
                    similarMovies,
                    forwardArrow = similarMovies.size >= 20,
                    onClick = { onClickMovie(it) },
                    lastElementClick = {})
            binding.similarMovies.adapter = similarMoviesAdapter

            binding.similarMoviesInformation.initTextLine(
                forwardText = similarMovies.size.toString()
            ) {
                //Forward to movie list page?
                onClickSimilarMovies(similarMovies)
                findNavController().navigate(R.id.action_moviePageFragment_to_moviesListPageFragment)
            }
        }
    }

    //Get GridLayoutManager with HORIZONTAL orientation
    private fun getLayoutManager(spanCount: Int): GridLayoutManager {
        return GridLayoutManager(
            requireContext(),
            spanCount,
            GridLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun createAppBarStateChangeListener(): AppBarStateChangeListener {
        return object : AppBarStateChangeListener() {
            val darkMagick = ViewTreeObserver.OnPreDrawListener {
                _binding?.scrollView?.scrollTo(0, 0)
                true
            }

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when (state) {
                    State.EXPANDED -> {
                        binding.appBarLayout.viewTreeObserver.addOnPreDrawListener(darkMagick)
                        binding.actionToolbar.navigationIcon = getDrawable(requireContext(), R.drawable.back_icon)
                        binding.toolbar.title = NO_TITLE
                    }

                    State.IDLE -> {
                        binding.appBarLayout.viewTreeObserver.removeOnPreDrawListener(darkMagick)
                        binding.actionToolbar.navigationIcon = getDrawable(requireContext(), R.drawable.back_icon)
                        binding.toolbar.title = NO_TITLE
                    }
                    else -> {
                        binding.appBarLayout.viewTreeObserver.removeOnPreDrawListener(darkMagick)
                        binding.actionToolbar.navigationIcon = getDrawable(requireContext(), R.drawable.back_icon_white)
                        binding.toolbar.title = mainViewModel.selectedMovie.value?.name()
                    }
                }
            }
        }
    }

    companion object {
        private const val DESCRIPTION_MAX_LINES = 5
        private const val IDLE_ANIMATION_STATE = 1
        private const val EXPANDING_ANIMATION_STATE = 2
        private const val COLLAPSING_ANIMATION_STATE = 3
        private const val ACTORS_NUMBER = 20
        private const val WORKERS_NUMBER = 6
        private const val ACTORS_LINE_COUNT = 4
        private const val WORKERS_LINE_COUNT = 2
        private const val PADDING = 20
        private const val NO_TITLE = " "
    }

}
