package woowacourse.movie.view.home.seat

import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import woowacourse.movie.domain.model.seat.Column
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat

class SeatView(
    private val seatTable: TableLayout,
    private val onSeatClick: (Seat) -> Unit,
) {
    private val rows = mutableListOf<SeatRow>()

    fun initSeats() {
        seatTable.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                val rowManager = SeatRow(row, rowIndex, onSeatClick)
                rowManager.initSeats()
                rows.add(rowManager)
            }
    }

    fun updateSeats(selectedSeats: Set<Seat>) {
        val selectedCoords = selectedSeats.map { Seat(Column(it.x.value), Row(it.y.value)) }.toSet()
        rows.forEach { it.updateSeats(selectedCoords) }
    }
}
