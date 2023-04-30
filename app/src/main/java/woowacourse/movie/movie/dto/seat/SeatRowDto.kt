package woowacourse.movie.movie.dto.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.R

@Parcelize
data class SeatRowDto(val row: Char) : Parcelable {

    fun getColor(): Int {
        return when (row) {
            'A', 'B' -> R.color.b_seat_color
            'C', 'D' -> R.color.s_seat_color
            else -> R.color.a_seat_color
        }
    }

    companion object {
        fun of(row: Int): SeatRowDto = SeatRowDto((row + 64).toChar())
    }
}
