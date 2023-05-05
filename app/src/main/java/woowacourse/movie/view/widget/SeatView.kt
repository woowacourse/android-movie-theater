package woowacourse.movie.view.widget

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TableRow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.SeatViewData
import woowacourse.movie.databinding.ItemSeatBinding

class SeatView private constructor(
    context: Context,
    val data: SeatViewData,
    var isSeatSelected: Boolean = false
) : ConstraintLayout(context) {
    private lateinit var binding: ItemSeatBinding
    private fun initLayout() {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_seat, this, true)

        layoutParams = TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        setBackgroundColor(Color.WHITE)
    }

    private fun initText() {
        binding.itemSeatText.text = context.getString(
            R.string.seat_row_column, data.rowCharacter, data.column + COLUMN_FIXER
        )
        binding.itemSeatText.setTextColor(context.getColor(data.color))
    }

    private fun selectSeat(selectable: () -> Boolean) {
        if (!isSeatSelected && selectable()) {
            isSeatSelected = true
            setBackgroundColor(context.getColor(R.color.selected_seat))
        } else if (isSeatSelected) {
            isSeatSelected = false
            setBackgroundColor(Color.WHITE)
        }
    }

    companion object {
        private const val COLUMN_FIXER = 1
        fun from(
            context: Context,
            seat: SeatViewData,
            selectable: () -> Boolean,
            onSelect: () -> Unit
        ): SeatView {
            return SeatView(context, seat).apply {
                initLayout()
                initText()
                setOnClickListener {
                    selectSeat(selectable)
                    onSelect()
                }
            }
        }
    }
}
