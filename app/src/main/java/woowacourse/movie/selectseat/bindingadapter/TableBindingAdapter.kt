package woowacourse.movie.selectseat.bindingadapter

import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.selectseat.uimodel.Position
import woowacourse.movie.selectseat.uimodel.SeatState
import woowacourse.movie.selectseat.uimodel.SeatUiModel

@BindingAdapter("settingSeats")
fun setTableItems(
    table: TableLayout,
    seatMap: Map<Position, SeatUiModel>,
) {
    seatMap.forEach { (position, seat) ->
        val tv = tableChildView(table, position.row, position.col)
        tv.setTextColor(ContextCompat.getColor(tv.context, seat.rateColor.color))
        val backgroundColorId =
            if (seat.state == SeatState.SELECTED) {
                R.color.yellow
            } else {
                R.color.white
            }
        val backgroundColor = ContextCompat.getColor(tv.context, backgroundColorId)
        tv.setBackgroundColor(backgroundColor)
        tv.text = seat.showPosition
    }
}

private fun tableChildView(
    table: TableLayout,
    row: Int,
    col: Int,
): TextView {
    val tableRow = table.getChildAt(row) as TableRow
    return tableRow.getChildAt(col) as TextView
}
