package woowacourse.movie.model

import android.os.Parcelable
import java.time.LocalDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketsState(
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val positions: List<SeatPositionState>
) : Parcelable {
    companion object {
        fun from(ticketOptState: TicketOptState, seats: List<SeatPositionState>): TicketsState {
            require(ticketOptState.countState.value == seats.size) {
                ERROR_NO_MATCH_SEAT_SIZE
            }
            return TicketsState(
                ticketOptState.movieState,
                ticketOptState.dateTime,
                seats.toList()
            )
        }

        private const val ERROR_NO_MATCH_SEAT_SIZE = "[ERROR] 좌석의 개수를 모두 골라주세요"
    }
}
