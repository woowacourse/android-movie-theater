package woowacourse.movie.view.seatselect

import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import woowacourse.movie.R
import woowacourse.movie.model.SeatUiModel
import woowacourse.movie.setBackgroundColorId

class SeatView(
    val view: TextView,
    val row: Char,
    val col: Int,
    updateViewBySeatClick: (SeatUiModel) -> Unit,
) {
    init {
        view.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.MATCH_PARENT,
            1f,
        )
        view.text = row.toString() + col.toString()
        view.textSize = 22f
        view.gravity = Gravity.CENTER
        view.isSelected = false
        setBackgroundColorId(R.color.white)
        view.setOnClickListener { updateViewBySeatClick(SeatUiModel(this.row, this.col)) }
    }

    fun setBackgroundColorId(color: Int) {
        view.setBackgroundColorId(color)
    }

    fun setTextColorById(color: Int) {
        view.setTextColor(
            ContextCompat.getColor(
                view.context,
                color,
            ),
        )
    }
}
