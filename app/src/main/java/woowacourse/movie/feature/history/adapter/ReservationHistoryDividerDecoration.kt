package woowacourse.movie.feature.history.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class ReservationHistoryDividerDecoration(
    private val height: Float = DEFAULT_HEIGHT,
    private val padding: Float = DEFAULT_PADDING,
    @ColorInt
    private val dividerColor: Int = DEFAULT_COLOR,
) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply { color = dividerColor }

    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val left = parent.paddingStart + padding
        val right = parent.width - parent.paddingEnd - padding

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height

            c.drawRect(left, top, right, bottom, paint)
        }
    }

    companion object {
        private const val DEFAULT_HEIGHT = 1f
        private const val DEFAULT_PADDING = 0f
        private const val DEFAULT_COLOR = Color.GRAY
    }
}
