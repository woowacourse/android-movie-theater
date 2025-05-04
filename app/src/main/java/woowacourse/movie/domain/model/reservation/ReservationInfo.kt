package woowacourse.movie.domain.model.reservation

import woowacourse.movie.domain.model.cinema.Seat
import java.time.LocalDateTime

class ReservationInfo(
    val title: String,
    val reservationDateTime: LocalDateTime,
    val reservationCount: ReservationCount,
) {
    private val _seats: MutableSet<Seat> = mutableSetOf()
    val seats: List<Seat> get() = _seats.toList()

    fun addSeat(seat: Seat) {
        validateReservationCount()
        _seats.add(seat)
    }

    fun removeSeat(seat: Seat) {
        _seats.remove(seat)
    }

    fun hasSeat(seat: Seat): Boolean = _seats.contains(seat)

    fun canPublish(): Boolean = _seats.size == reservationCount.value

    private fun validateReservationCount() {
        require(reservationCount.value > _seats.count()) {
            INVALID_SEATS_SIZE_ERROR_MESSAGE.format(
                reservationCount.value,
                _seats.count(),
            )
        }
    }

    companion object {
        private const val INVALID_SEATS_SIZE_ERROR_MESSAGE =
            "예매 인원 수보다 많은 좌석을 선택할 수 없습니다. (총 인원: %d / 선택 좌석: %d)"
    }
}
