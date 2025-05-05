package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Ticket(
    val title: String,
    val showTime: LocalDateTime,
    val seats: List<Seat>,
    val reservationCount: ReservationCount,
    val cinema: Cinema,
) : Parcelable {
    init {
        require(reservationCount.value >= seats.count()) {
            INVALID_SEATS_SIZE_ERROR_MESSAGE.format(reservationCount.value, seats.count())
        }
    }

    fun totalPrice(): Int = seats.sumOf { it.price() }

    fun updateSeats(seat: Seat): Ticket {
        return if (seats.contains(seat)) {
            copy(
                seats = seats - seat,
            )
        } else {
            copy(
                seats = seats + seat,
            )
        }
    }

    companion object {
        private const val INVALID_SEATS_SIZE_ERROR_MESSAGE =
            "예매 인원 수보다 많은 좌석을 선택할 수 없습니다. (총 인원: %d / 선택 좌석: %d)"
    }
}
