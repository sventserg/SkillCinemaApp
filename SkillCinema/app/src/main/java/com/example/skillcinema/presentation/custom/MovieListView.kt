package com.example.skillcinema.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.skillcinema.R
import com.example.skillcinema.databinding.MovieListPreviewBinding
import com.example.skillcinema.presentation.viewmodel.adapter.movieList.MovieListAdapter

class MovieListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: MovieListPreviewBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.movie_list_preview, this, true)
        binding = MovieListPreviewBinding.bind(this)
        attributesInit(attrs, defStyleAttr, defStyleRes)
    }

    private fun attributesInit(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.MovieListView,
            defStyleAttr,
            defStyleRes
        )

        val movieListName = typedArray.getString(R.styleable.MovieListView_movieListName)
        val allText = typedArray.getString(R.styleable.MovieListView_movieForwardText)
        binding.movieListName.text = movieListName ?: context.getString(R.string.unknown)
        binding.allTextView.text = allText ?: context.getString(R.string.all)

        typedArray.recycle()
    }

    fun initView(
        adapter: MovieListAdapter? = null,
        movieListName: String? = null,
        forwardButtonText: String? = null,
        itemDecoration: ItemDecoration? = null,
        onForwardButtonClick: () -> Unit
    ) {
        if (adapter != null) {
            binding.movieListContainer.adapter = adapter
            binding.root.visibility = View.VISIBLE
        } else binding.root.visibility = View.GONE
        if (movieListName != null) binding.movieListName.text = movieListName
        if (forwardButtonText != null) binding.allTextView.text = forwardButtonText
        if (itemDecoration != null) binding.movieListContainer.addItemDecoration(itemDecoration)
        binding.forwardButton.setOnClickListener { onForwardButtonClick() }
    }

    fun addDecoration(
        itemDecoration: ItemDecoration
    ) {
        binding.movieListContainer.addItemDecoration(itemDecoration)
    }
}