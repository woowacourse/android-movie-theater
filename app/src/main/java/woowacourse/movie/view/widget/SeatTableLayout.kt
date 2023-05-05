package woowacourse.movie.view.widget

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import woowacourse.movie.data.SeatTableViewData
import woowacourse.movie.data.SeatsViewData
import woowacourse.movie.system.getSerializableCompat

class SeatTableLayout(
    private val tableLayout: TableLayout,
    override val saveStateKey: String
) : SaveState {
    var onSelectSeat: (SeatsViewData) -> Unit = {}
    var seatSelectCondition: (Int) -> Boolean = { true }

    fun selectedSeats(): SeatsViewData {
        return tableLayout.children.flatMap { tableRow ->
            (tableRow as TableRow).children.filterIsInstance<SeatView>().filter {
                it.isSeatSelected
            }
        }.map {
            it.data
        }.let {
            SeatsViewData(it.toList())
        }
    }

    fun makeSeatTable(seats: SeatTableViewData) {
        tableLayout.weightSum = seats.size.row.toFloat()
        (0 until seats.size.row).forEach {
            tableLayout.addView(makeSeatRow(it, seats))
        }
    }

    private fun makeSeatRow(row: Int, seats: SeatTableViewData): TableRow {
        val tableRow = TableRow(tableLayout.context)
        tableRow.weightSum = seats.size.column.toFloat()
        tableRow.layoutParams = TableLayout.LayoutParams(0, 0, 1f)
        (0 until seats.size.column).forEach {
            tableRow.addView(makeSeatCell(row, it, seats))
        }
        return tableRow
    }

    private fun makeSeatCell(
        row: Int,
        column: Int,
        seats: SeatTableViewData
    ): SeatView {
        return SeatView.from(
            tableLayout.context,
            seats.getSeat(row, column),
            { seatSelectCondition(selectedSeats().value.size) }
        ) {
            onSelectSeat(selectedSeats())
        }
    }

    override fun save(outState: Bundle) {
        outState.putSerializable(saveStateKey, selectedSeats())
    }

    override fun load(savedInstanceState: Bundle?) {
        savedInstanceState ?: return
        val seats = savedInstanceState.getSerializableCompat<SeatsViewData>(saveStateKey) ?: return
        seats.value.forEach {
            findSeatViewByRowAndColumn(it.row, it.column)?.callOnClick()
        }
    }

    private fun findSeatViewByRowAndColumn(row: Int, column: Int): SeatView? {
        return tableLayout.children.flatMap { tableRow ->
            (tableRow as TableRow).children
        }.filterIsInstance<SeatView>().find {
            it.data.row == row && it.data.column == column
        }
    }

    companion object {
        fun from(
            tableLayout: TableLayout,
            seatTableViewData: SeatTableViewData,
            saveStateKey: String
        ): SeatTableLayout {
            return SeatTableLayout(tableLayout, saveStateKey).also {
                it.makeSeatTable(seatTableViewData)
            }
        }
    }
}
