package woowacourse.movie.bindingadapter

import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import woowacourse.movie.selectseat.uimodel.SeatsUiModel

@BindingAdapter("settingSeats")
fun setTableItems(
    table: TableLayout,
    seats: SeatsUiModel,
) {
    seats.seats.forEach { seat ->
        val chb = tableChildView(table, seat.row, seat.col)
        chb.setTextColor(ContextCompat.getColor(chb.context, seat.rateColor.color))
        chb.text = seat.showPosition
    }
}

private fun tableChildView(
    table: TableLayout,
    row: Int,
    col: Int,
): CheckBox {
    val tableRow = table.getChildAt(row) as TableRow
    return tableRow.getChildAt(col) as CheckBox
}
