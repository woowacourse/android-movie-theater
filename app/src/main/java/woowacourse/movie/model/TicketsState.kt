package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.ui.main.itemModel.TicketsItemModel
import java.time.LocalDateTime

@Parcelize
data class TicketsState(
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val positions: List<SeatPositionState>
) : Parcelable {

    fun convertToItemModel(onClick: (position: Int) -> Unit): TicketsItemModel {
        return TicketsItemModel(this, onClick)
    }

    companion object {
        fun from(reservationState: ReservationOptState, seats: List<SeatPositionState>): TicketsState {
            require(reservationState.countState.value == seats.size) {
                ERROR_NO_MATCH_SEAT_SIZE
            }
            return TicketsState(
                reservationState.movieState,
                reservationState.dateTime,
                seats.toList()
            )
        }

        private const val ERROR_NO_MATCH_SEAT_SIZE = "[ERROR] 좌석의 개수를 모두 골라주세요"
    }
}
