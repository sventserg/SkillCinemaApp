package com.example.skillcinema.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.skillcinema.R
import com.example.skillcinema.databinding.CustomMovieInformationTextLineBinding

class MovieInformationTextLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: CustomMovieInformationTextLineBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.custom_movie_information_text_line, this, true)
        binding = CustomMovieInformationTextLineBinding.bind(this)
        attributesInit(attrs, defStyleAttr, defStyleRes)
    }

    private fun attributesInit(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.MovieInformationTextLine,
            defStyleAttr,
            defStyleRes
        )

        binding.forwardText.text =
            typedArray.getString(R.styleable.MovieInformationTextLine_forwardText)
                ?: context.getString(R.string.all)
        binding.listName.text = typedArray.getString(R.styleable.MovieInformationTextLine_listName)
            ?: context.getString(R.string.unknown)

        typedArray.recycle()
    }

    fun initTextLine(
        listName: String? = null,
        forwardText: String? = null,
        onForwardButtonClick: () -> Unit
    ) {
        if (listName != null) binding.listName.text = listName
        if (forwardText != null) binding.forwardText.text = forwardText
        binding.forwardButton.setOnClickListener { onForwardButtonClick() }
    }

}