package woowacourse.movie.seat.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeatDto(val row: SeatRowDto, val col: SeatColDto) : Parcelable {
    fun getString(): String {
        return "${row.row}${col.col}"
    }
}
