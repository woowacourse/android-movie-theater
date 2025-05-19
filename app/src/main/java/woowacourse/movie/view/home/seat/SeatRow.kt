package woowacourse.movie.view.home.seat

import android.graphics.Color
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import woowacourse.movie.domain.model.seat.Col
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat

class SeatRow(
    private val row: TableRow,
    private val rowIndex: Int,
    private val onSeatClick: (Seat) -> Unit,
) {
    private val seatViews = mutableMapOf<Seat, TextView>()

    fun initSeats() {
        row.children
            .filterIsInstance<TextView>()
            .forEachIndexed { colIndex, view ->
                val position = Seat(Col(colIndex), Row(rowIndex))
                view.tag = position
                seatViews[position] = view
                view.setOnClickListener { onSeatClick(position) }
            }
    }

    fun updateSeats(selectedPositions: Set<Seat>) {
        seatViews.forEach { (position, view) ->
            view.setBackgroundColor(
                if (position in selectedPositions) Color.YELLOW else Color.TRANSPARENT,
            )
        }
    }
}
