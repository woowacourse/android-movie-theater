package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReservationInfo(
    val title: String,
    val reservationDateTime: LocalDateTime,
    val reservationCount: ReservationCount,
    val seats: List<Seat> = listOf(),
    val cinema: Cinema,
) : Parcelable {
    // 좌석 선택/해제
    fun updateSeats(seat: Seat): ReservationInfo {
        if (seats.contains(seat)) {
            seat.isSelected = false
            return this.copy(seats = seats - seat)
        }

        require(reservationCount.value > seats.count()) {
            INVALID_SEATS_SIZE_ERROR_MESSAGE.format(reservationCount.value, seats.count())
        }

        seat.isSelected = true
        return this.copy(seats = seats + seat)
    }

    fun totalPrice(): Int = seats.sumOf { it.price() }

    companion object {
        private const val INVALID_SEATS_SIZE_ERROR_MESSAGE =
            "예매 인원 수보다 많은 좌석을 선택할 수 없습니다. (총 인원: %d / 선택 좌석: %d)"
    }
}
