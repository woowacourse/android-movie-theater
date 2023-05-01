package woowacourse.movie.movie.dto.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeatsDto(val seats: List<SeatDto> = emptyList()) : Parcelable {
    fun getSeatsPositionToString(): String {
        return seats.joinToString(", ") { it.getString() }
    }
}
