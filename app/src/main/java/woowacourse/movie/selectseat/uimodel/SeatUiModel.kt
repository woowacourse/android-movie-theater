package woowacourse.movie.selectseat.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.SeatRate
import woowacourse.movie.model.SeatState

@Parcelize
data class SeatUiModel(
    val showPosition: String,
    val rateColor: RateColor,
    val row: Int,
    val col: Int,
    val state: SeatState = SeatState.NONE,
) : Parcelable {
    constructor(row: Int, col: Int, seatRate: SeatRate) : this(
        positionFormat(row, col),
        color(seatRate),
        row,
        col,
    )

    companion object {
        private const val START_ROW_ALPHABET = 'A'
        private const val SHOW_COL_FORMAT = 1

        private fun positionFormat(
            row: Int,
            col: Int,
        ): String {
            val rowLetter = START_ROW_ALPHABET + row
            return "$rowLetter${col + SHOW_COL_FORMAT}"
        }

        private fun color(rate: SeatRate): RateColor =
            when (rate) {
                SeatRate.S -> RateColor.GREEN
                SeatRate.A -> RateColor.BLUE
                SeatRate.B -> RateColor.PURPLE
            }
    }
}
