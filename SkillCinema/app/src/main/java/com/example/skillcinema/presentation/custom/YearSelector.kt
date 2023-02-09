package com.example.skillcinema.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ChipBinding
import com.example.skillcinema.databinding.YearSelectorBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class YearSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: YearSelectorBinding
    private var _selectedYear = MutableStateFlow<Int?>(null)
    val selectedYear = _selectedYear.asStateFlow()

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.year_selector, this, true)
        binding = YearSelectorBinding.bind(this)
        attributesInit(attrs, defStyleAttr, defStyleRes)
    }

    private fun attributesInit(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.YearSelector,
            defStyleAttr,
            defStyleRes
        )
        val endYear = typedArray.getInt(R.styleable.YearSelector_year, 2011)
        initChipGroup(endYear)
        typedArray.recycle()
    }

    fun initYearSelector(
        year: Int,
        onBackPress: () -> Unit,
        onForwardPress: () -> Unit
    ) {
        binding.chipGroup.removeAllViews()
        initChipGroup(year)

        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                _selectedYear.value = checkedIds.first()
                Log.d("YEAR_SELECTOR", "Selected year: ${selectedYear.value}")
            }
        }

        binding.backButton.setOnClickListener { onBackPress() }
        binding.forwardButton.setOnClickListener { onForwardPress() }
    }

    fun clearSelectedYear() {
            binding.chipGroup.clearCheck()
            _selectedYear.value = null
    }

    private fun chip(year: Int): Chip {
        val chipBinding = ChipBinding.inflate(LayoutInflater.from(context))
        chipBinding.chip.text = year.toString()
        chipBinding.chip.id = year
        return chipBinding.root
    }

    private fun initChipGroup(year: Int) {
        val startYear = year - 11
        binding.yearRange.text = context.getString(R.string.number_range, startYear, year)

        val yearList = mutableListOf<Int>()
        for (i in 0..11) {
            yearList.add(startYear + i)
        }
        yearList.forEach {
            binding.chipGroup.addView(chip(it))
        }
    }

}