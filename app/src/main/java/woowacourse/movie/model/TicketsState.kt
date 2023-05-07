package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.common.itemModel.TicketsItemModel
import java.time.LocalDateTime

@Parcelize
data class TicketsState(
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val positions: List<SeatPositionState>
) : Parcelable {

    fun toItemModel(onClick: (position: Int) -> Unit): TicketsItemModel {
        return TicketsItemModel(this, onClick)
    }

    companion object {
        fun from(ticketOptState: TicketOptState, seats: List<SeatPositionState>): TicketsState {
            require(ticketOptState.count == seats.size) {
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
