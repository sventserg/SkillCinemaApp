package com.example.skillcinema.presentation.decorator

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class HorizontalDividerDecoration(private val drawable: Drawable?) : RecyclerView.ItemDecoration() {

    val width = drawable?.intrinsicWidth
    val height = drawable?.intrinsicHeight

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let { adapter ->
            val position = parent.getChildAdapterPosition(view)
                .let { if (it == RecyclerView.NO_POSITION) return else it }
            outRect.bottom = if (position == adapter.itemCount - 1 || width == null) 0
            else width
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children.forEach { view ->
                val position = parent.getChildAdapterPosition(view)
                    .let { if (it == RecyclerView.NO_POSITION) return else it }
                if (position != adapter.itemCount - 1 && height != null) {
                    val left = parent.paddingLeft
                    val top = view.bottom
                    val right = parent.width - parent.paddingRight
                    val bottom = top + height
                    drawable?.bounds = Rect(left, top, right, bottom)
                    drawable?.draw(c)
                }
            }
        }
    }
}