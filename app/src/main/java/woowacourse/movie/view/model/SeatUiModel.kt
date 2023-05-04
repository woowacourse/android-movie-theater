package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SeatUiModel(val row: Int, val col: Int) : Parcelable {
    val seatId: String = ('A'.code + row).toChar() + (col + 1).toString()
}
