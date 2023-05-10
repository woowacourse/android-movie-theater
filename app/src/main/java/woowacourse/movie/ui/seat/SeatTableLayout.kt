package woowacourse.movie.ui.seat

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import woowacourse.movie.R

class SeatTableLayout(context: Context, attrs: AttributeSet) : TableLayout(context, attrs) {

    init {
        this.isStretchAllColumns = true
        this.isShrinkAllColumns = true
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
    }

    fun addRow(rowSize: Int, columnSize: Int) {
        repeat(rowSize) {
            addView(makeRow(columnSize))
        }
    }

    private fun makeRow(columnSize: Int): TableRow {
        val row = TableRow(context)
        row.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.MATCH_PARENT,
        )

        repeat(columnSize) {
            row.addView(makeTextView())
        }

        return row
    }

    private fun makeTextView(): TextView {
        val seat = TextView(context, null, 0, R.style.SeatTableText)
        seat.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT,
        )
        seat.setBackgroundResource(R.drawable.seat_selector)
        return seat
    }

    fun setOnSeatSelectListener(onSeatSelected: (row: Int, col: Int) -> Unit) {
        this.loopTable { rowIdx, columnIdx, seat ->
            seat.setOnClickListener {
                onSeatSelected(rowIdx, columnIdx)
            }
        }
    }

    fun initSeatsText() {
        this.loopTable { rowIdx, columnIdx, seat ->
            val seatText =
                (context.getString(R.string.start_row).first().code + rowIdx).toChar() +
                    (context.getString(R.string.start_column).toInt() + columnIdx).toString()

            seat.text = seatText
            setSeatTextColor(seat, rowIdx)
        }
    }

    private fun setSeatTextColor(seat: TextView, row: Int) {
        when (row) {
            in MAGENTA_RANGE -> seat.setTextColor(Color.parseColor(context.getString(R.string.purple)))
            in GREEN_RANGE -> seat.setTextColor(Color.GREEN)
            else -> seat.setTextColor(Color.BLUE)
        }
    }

    fun setSelected(row: Int, col: Int) {
        this.loopTable { rowIdx, columnIdx, seat ->
            if (rowIdx == row && columnIdx == col) {
                with(seat) {
                    if (isSelected) {
                        setBackgroundColor(Color.WHITE)
                    } else {
                        setBackgroundColor(Color.YELLOW)
                    }
                    isSelected = !isSelected
                }
                return@loopTable
            }
        }
    }

    private fun TableLayout.loopTable(action: (Int, Int, TextView) -> Unit) {
        this.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIdx, row ->
                row.loopRow { columnIdx, textView ->
                    action(rowIdx, columnIdx, textView)
                }
            }
    }

    private fun TableRow.loopRow(action: (Int, TextView) -> Unit) {
        children.filterIsInstance<TextView>()
            .forEachIndexed { columnIdx, textView ->
                action(columnIdx, textView)
            }
    }

    companion object {

        private val MAGENTA_RANGE = 0..1
        private val GREEN_RANGE = 2..3
    }
}
