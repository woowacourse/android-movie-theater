package woowacourse.movie.presentation.home.reservation.seat

import android.content.Context
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.presentation.common.model.SeatTypeUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel

class SeatViewFactory(
    private val context: Context,
) {
    fun create(
        seat: SeatUiModel,
        isSelected: Boolean,
    ): TextView =
        TextView(context).apply {
            text = seat.toLabel()
            gravity = Gravity.CENTER
            textSize = 22f
            setTextColor(seat.type.toColor(context))

            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            post {
                layoutParams.height = width
                requestLayout()
            }

            setTag(R.id.seat_selected, isSelected)
            setBackgroundResource(if (isSelected) R.color.yellow_fa else R.color.white)
        }

    private fun SeatTypeUiModel.toColor(context: Context): Int =
        when (this) {
            SeatTypeUiModel.S_CLASS -> context.getColor(R.color.purple_8e)
            SeatTypeUiModel.A_CLASS -> context.getColor(R.color.green_19)
            SeatTypeUiModel.B_CLASS -> context.getColor(R.color.blue_1b)
        }
}
