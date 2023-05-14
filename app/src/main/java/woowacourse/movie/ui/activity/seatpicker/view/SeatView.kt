package woowacourse.movie.ui.activity.seatpicker.view

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import woowacourse.movie.R
import woowacourse.movie.ui.model.seat.SeatModel

class SeatView(
    context: Context,
    private val parent: View,
    private val columnSize: Int,
    private val onClick: (Boolean) -> Unit
) : AppCompatTextView(context) {
    fun bind(seat: SeatModel) {
        background = ContextCompat.getDrawable(context, R.drawable.selector_seat)
        gravity = Gravity.CENTER
        textSize = 22f
        text = context.getString(R.string.seat, seat.row.letter, seat.column.value)
        setTextColor(context.getColor(seat.rank.color))
        setOnClickListener { onClick(isSelected) }
    }

    fun setTextViewSelected(selected: Boolean) {
        isSelected = selected
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = parent.measuredWidth / columnSize
        setMeasuredDimension(width, width)
    }
}
