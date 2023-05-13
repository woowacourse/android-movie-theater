package woowacourse.movie.dto.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.dto.seat.SeatColDto
import woowacourse.movie.dto.seat.SeatRowDto

@Parcelize
data class SeatDto(val row: SeatRowDto, val col: SeatColDto) : Parcelable {
    fun getString(): String {
        return "${row.row}${col.col}"
    }
}
