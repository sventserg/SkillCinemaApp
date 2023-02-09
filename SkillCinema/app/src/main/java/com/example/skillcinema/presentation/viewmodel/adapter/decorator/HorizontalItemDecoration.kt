package com.example.skillcinema.presentation.viewmodel.adapter.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDecoration(
    private val firstElementMargin: Int,
    private val horizontalSpacing: Int,
    private val lineCount: Int = 1,
    private val verticalSpacing: Int = 0
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        //When lineCount = 1
        if (lineCount == 1) {
            parent.adapter?.let {
                when (parent.getChildAdapterPosition(view)) {
                    0 -> {
                        outRect.left = firstElementMargin
                        outRect.right = horizontalSpacing / 2
                    }
                    it.itemCount - 1 -> {
                        outRect.left = horizontalSpacing / 2
                        outRect.right = firstElementMargin
                    }
                    else -> {
                        outRect.left = horizontalSpacing / 2
                        outRect.right = horizontalSpacing / 2
                    }
                }
            }
        }

        //When lineCount > 1
        else if (lineCount > 1) {
            parent.adapter?.let {
                val reminder = it.itemCount % lineCount
                val columnCount = if (reminder == 0) it.itemCount / lineCount
                 else (it.itemCount / lineCount) + 1
                val position = parent.getChildAdapterPosition(view)

                if (columnCount > 1) {
                    when (position / lineCount) {

                        // First column
                        0 -> {
                            outRect.left = firstElementMargin
                            outRect.right = horizontalSpacing / 2
                        }

                        //Last column
                        columnCount - 1 -> {
                            outRect.right = firstElementMargin
                            outRect.left = horizontalSpacing / 2
                        }

                        //Other columns
                        else -> {
                            outRect.right = horizontalSpacing / 2
                            outRect.left = horizontalSpacing / 2
                        }
                    }
                } else {

                    //When there is only one column
                    outRect.right = firstElementMargin
                    outRect.left = firstElementMargin
                }

                // First line
                if (position % lineCount == 0) {
                    outRect.bottom = verticalSpacing / 2
                }

                //Last line
                if ((position + 1) % lineCount == 0) {
                    outRect.top = verticalSpacing / 2
                }

                //Other lines
                if (position % lineCount != 0 && (position + 1) % lineCount != 0) {
                    outRect.top = verticalSpacing / 2
                    outRect.bottom = verticalSpacing / 2
                }
            }
        }
    }
}