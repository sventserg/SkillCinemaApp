package com.example.skillcinema.presentation.fragment.bottom

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.BottomFragmentAddMovieBinding
import com.example.skillcinema.databinding.DialogNewCollectionBinding
import com.example.skillcinema.entity.Movie
import com.example.skillcinema.presentation.adapter.addMovieToCollection.AddMovieToCollectionAdapter
import com.example.skillcinema.presentation.decorator.HorizontalDividerDecoration
import com.example.skillcinema.presentation.viewmodel.DatabaseViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class AddMovieToCollectionFragment(
    private val movie: Movie,
    private val databaseViewModel: DatabaseViewModel
) : BottomSheetDialogFragment() {
    private var _binding: BottomFragmentAddMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomFragmentAddMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let {
                val behaviour = BottomSheetBehavior.from(it)
                fullScreen(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide
            .with(binding.root)
            .load(movie.posterUrl)
            .centerCrop()
            .into(binding.moviePoster)

        var movieInformation = ""
        if (movie.year != null) movieInformation += "${movie.year.toString()}, "
        movieInformation += movie.genresText()
        binding.ratingText.text = movie.rating()
        binding.movieName.text = movie.name()
        binding.movieInformation.text = movieInformation

        val adapter = AddMovieToCollectionAdapter(movie, databaseViewModel)
        binding.collections.adapter = adapter
        val divider =
            HorizontalDividerDecoration(getDrawable(resources, R.drawable.horizontal_divider, null))
        binding.collections.addItemDecoration(divider)

        //Add new collection dialog
        val dialogNewCollectionBinding = DialogNewCollectionBinding.inflate(layoutInflater)
        val dialogNewCollection = Dialog(requireContext())
        dialogNewCollection.setContentView(dialogNewCollectionBinding.root)
        dialogNewCollection.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val addCollectionButton = dialogNewCollectionBinding.doneButton
        val newCollectionName = dialogNewCollectionBinding.editText
        val closeDialogNewCollectionButton = dialogNewCollectionBinding.closeButton
        closeDialogNewCollectionButton.setOnClickListener { dialogNewCollection.hide() }

        binding.addCollectionButton.setOnClickListener {
            dialogNewCollection.show()
            addCollectionButton.setOnClickListener {
                val text = newCollectionName.text.toString()
                if (text.isEmpty()) {
                    errorDialog(getString(R.string.no_collection_name))
                } else if (databaseViewModel.checkMovieListName(text)) {
                    errorDialog(getString(R.string.wrong_collection_name))
                } else viewLifecycleOwner.lifecycleScope.launch {
                    databaseViewModel.insertMovieList(text)
                    adapter.notifyItemInserted(databaseViewModel.collections.value.lastIndex)
                    dialogNewCollection.hide()
                }
            }
        }
        binding.closeButton.setOnClickListener { this.dismiss() }
    }

    private fun errorDialog(description: String) {
        val dialog =
            ErrorBottomFragment(description)
        val sfm = activity?.supportFragmentManager
        if (sfm != null) {
            dialog.show(sfm, "ErrorDialog")
        }
    }

    private fun fullScreen(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onDestroyView() {
        binding.collections.adapter = null
        _binding = null
        super.onDestroyView()
    }
}