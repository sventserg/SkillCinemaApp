package com.example.skillcinema.presentation.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SimpleVerticalItemDecoration(
    private val verticalSpacing: Int,
    private val lastElementMargin: Int? = null
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let {
            when (parent.getChildAdapterPosition(view)) {
                //First element
                0 -> outRect.bottom = verticalSpacing / 2

                //Last element
                it.itemCount - 1 -> {
                    outRect.top = verticalSpacing / 2
                    if (lastElementMargin != null) outRect.bottom = lastElementMargin
                }

                //Other elements
                else -> {
                    outRect.top = verticalSpacing / 2
                    outRect.bottom = verticalSpacing / 2
                }
            }
        }
    }
}