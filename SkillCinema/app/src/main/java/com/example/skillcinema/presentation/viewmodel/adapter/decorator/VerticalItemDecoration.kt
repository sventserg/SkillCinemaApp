package com.example.skillcinema.presentation.viewmodel.adapter.decorator

import android.graphics.Rect
import android.icu.text.ListFormatter.Width
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecoration(
    private val verticalSpacing: Int,
    private val horizontalSpacing: Int,
    private val displayWidth: Int,
    private val viewWidth: Int,
    private val columnCount: Int = 2
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let {
            val position = parent.getChildAdapterPosition(view)
            val reminder = it.itemCount % columnCount
            val lineCount = if (reminder == 0) it.itemCount / columnCount
            else (it.itemCount / columnCount) + 1
            if (columnCount > 1) {
                val margin =
                    (displayWidth - (viewWidth * columnCount) - (horizontalSpacing * (columnCount - 1))) / 2

                //First column
                if (position % columnCount == 0) {
                    outRect.left = margin
                    outRect.right = horizontalSpacing / 2
                }

                //Last column
                if ((position + 1) % columnCount == 0) {
                    outRect.left = horizontalSpacing / 2
                    outRect.right = margin
                }

                //Middle columns
                if (position % columnCount != 0 && (position + 1) % columnCount != 0) {
                    outRect.left = horizontalSpacing / 2
                    outRect.right = horizontalSpacing / 2
                }

                when (position / columnCount) {

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
}
