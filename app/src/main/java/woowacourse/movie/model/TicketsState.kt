package woowacourse.movie.model

import android.os.Parcelable
import java.time.LocalDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketsState(
    val cinemaName: String,
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val positions: List<SeatPositionState>
) : Parcelable {
    companion object {
        fun from(
            cinemaName: String,
            seatSelectState: SeatSelectState,
            seats: List<SeatPositionState>
        ): TicketsState {
            require(seatSelectState.countState.value == seats.size) {
                ERROR_NO_MATCH_SEAT_SIZE
            }
            return TicketsState(
                cinemaName,
                seatSelectState.movieState,
                seatSelectState.dateTime,
                seats.toList()
            )
        }

        private const val ERROR_NO_MATCH_SEAT_SIZE = "[ERROR] 좌석의 개수를 모두 골라주세요"
    }
}
