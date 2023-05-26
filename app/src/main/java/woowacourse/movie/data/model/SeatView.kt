package woowacourse.movie.data.model

import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import domain.Seat
import domain.seatPolicy.SeatPolicies
import woowacourse.movie.R
import woowacourse.movie.data.model.mapper.SeatRankMapper
import woowacourse.movie.data.model.uimodel.SeatUIModel
import woowacourse.movie.setBackgroundColorId

class SeatView(val view: TextView, val row: Char, val col: Int, onClick: (SeatView) -> Unit) {
    init {
        view.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f
        )
        view.text = row.toString() + col.toString()
        view.textSize = 22f
        view.gravity = Gravity.CENTER
        view.isSelected = false
        setBackgroundColorId(R.color.white)
        initTextColor()
        view.setOnClickListener { onClick(this) }
    }
    private fun initTextColor() {
        val seat = Seat(SeatUIModel.toNumber(row), col, SeatPolicies())
        val seatRankViewModel = SeatRankMapper.toUI(seat.rank)
        view.setTextColor(
            ContextCompat.getColor(
                view.context, seatRankViewModel.color
            )
        )
    }

    fun setBackgroundColorId(color: Int) {
        view.setBackgroundColorId(color)
    }
}
