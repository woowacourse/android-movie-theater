package woowacourse.movie.view.bindingadapter

import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import woowacourse.movie.domain.model.seat.Column
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.view.home.seat.SeatRow

@BindingAdapter("onClickSeat")
fun TableLayout.seatClickListener(handler: SeatRow.Handler) {
    children
        .filterIsInstance<TableRow>()
        .forEachIndexed { rowIdx, row ->
            row.children
                .filterIsInstance<TextView>()
                .forEachIndexed { colIdx, view ->
                    val coord = Seat(Column(rowIdx + 1), Row(colIdx + 1))
                    view.setOnClickListener { handler.onClickSeat(coord) }
                }
        }
}
