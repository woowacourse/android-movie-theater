package woowacourse.movie.view.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import kotlinx.parcelize.Parcelize

@Parcelize
class SeatUiModel(val row: Int, val col: Int, @ColorRes val color: Int) : Parcelable {
    val seatId: String = ('A'.code + row).toChar() + (col + 1).toString()

    companion object {
        fun of(seatId: String, @ColorRes color: Int = 0): SeatUiModel {
            val row = seatId[0].code - 'A'.code
            val col = seatId.substring(1).toInt() - 1
            return SeatUiModel(row, col, color)
        }
    }
}
