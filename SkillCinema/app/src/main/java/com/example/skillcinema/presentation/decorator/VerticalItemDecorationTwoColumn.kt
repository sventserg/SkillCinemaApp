package com.example.skillcinema.presentation.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecorationTwoColumn(
    private val verticalSpacing: Int,
    private val horizontalSpacing: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let {
            val position = parent.getChildAdapterPosition(view)
            val reminder = it.itemCount % 2
            val lineCount = if (reminder == 0) it.itemCount / 2
            else (it.itemCount / 2) + 1

            //First column
            if (position % 2 == 0) {
                outRect.right = horizontalSpacing / 2
            }

            //Last column
            if (position % 2 != 0) {
                outRect.left = horizontalSpacing / 2
            }


            when (position / 2) {

                //First line
                0 -> {
                    outRect.bottom = verticalSpacing / 2
                }

                //Last line
                lineCount - 1 -> {
                    outRect.top = verticalSpacing / 2
                }

                //Middle lines
                else -> {
                    outRect.top = verticalSpacing / 2
                    outRect.bottom = verticalSpacing / 2
                }
            }
        }
    }
}