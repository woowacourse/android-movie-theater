package woowacourse.movie.view.seat

import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import woowacourse.movie.domain.model.seat.Seat

class SeatView(
    private val seatTable: TableLayout,
) {
    private val rows = mutableListOf<SeatRowView>()

    fun initSeats() {
        seatTable.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                val row = SeatRowView(row, rowIndex)
                row.initSeats()
                rows.add(row)
            }
    }

    fun updateSeats(selectedSeats: Set<Seat>) {
        rows.forEach { it.updateSeats(selectedSeats) }
    }

    fun selectedSeat(): List<Seat> {
        return rows.flatMap { it.selectedSeat() }
    }
}
