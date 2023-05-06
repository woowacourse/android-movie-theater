package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SeatUiModel(val row: Int, val col: Int) : Parcelable {
    val seatId: String = ('A'.code + row).toChar() + (col + 1).toString()

    companion object {
        fun of(seatId: String): SeatUiModel {
            val row = seatId[0].code - 'A'.code
            val col = seatId.substring(1..seatId.lastIndex).toInt() - 1
            return SeatUiModel(row, col)
        }
    }
}
