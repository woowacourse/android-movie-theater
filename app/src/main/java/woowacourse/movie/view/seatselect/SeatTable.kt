package woowacourse.movie.view.seatselect

import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.model.SeatUiModel
import woowacourse.movie.model.TicketsUiModel

class SeatTable(
    private val tableLayout: TableLayout,
    val rowSize: Int = DEFAULT_ROW_SIZE,
    val colSize: Int = DEFAULT_COL_SIZE,
    private val onClick: (SeatView) -> Unit
) {
    private val seatViews: MutableList<SeatView> = mutableListOf()
    fun makeSeatTable() {
        repeat(rowSize) { row ->
            val tableRow = makeTableRow()
            repeat(colSize) { col ->
                val seatView = SeatView(
                    TextView(tableLayout.context), SeatUiModel.toChar(row + 1), col + 1, onClick
                )
                seatViews.add(seatView)
                tableRow.addView(seatView.view)
            }
            tableLayout.addView(tableRow)
        }
    }

    fun updateTable(ticketsUiModel: TicketsUiModel) {
        initTableBackground()
        ticketsUiModel.list.forEach { ticketUiModel ->
            val index = convertTablePositionToIndex(ticketUiModel.seat)
            seatViews[index].view.isSelected = true
            seatViews[index].setBackgroundColorId(R.color.seat_selection_selected_seat_color)
        }
    }

    private fun initTableBackground() {
        seatViews.forEach {
            it.view.isSelected = false
            it.setBackgroundColorId(R.color.white)
        }
    }

    private fun convertTablePositionToIndex(seatUiModel: SeatUiModel): Int {
        return (SeatUiModel.toNumber(seatUiModel.row) - 1) * (colSize) + (seatUiModel.col - 1)
    }

    private fun makeTableRow(): TableRow {
        val tableRow = TableRow(tableLayout.context)
        tableRow.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.MATCH_PARENT,
            TABLE_WEIGHT
        )
        return tableRow
    }

    companion object {
        private const val DEFAULT_ROW_SIZE = 5
        private const val DEFAULT_COL_SIZE = 4
        private const val TABLE_WEIGHT = 1f
    }
}
