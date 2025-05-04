package woowacourse.movie.view.home.seat

import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
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
        rows.forEach { it.updateSeats(selectedSeats) }
    }

    fun selectedSeat(): List<Seat> {
        return rows.flatMap { it.selectedSeat() }
    }
}
