package woowacourse.movie.domain

import woowacourse.movie.domain.reservationNotificationPolicy.ReservationNotificationPolicy
import woowacourse.movie.domain.seat.Seats
import java.time.LocalDateTime

data class Reservation(
    val movie: Movie,
    val reservationDetail: ReservationDetail,
    val seats: Seats,
    val price: Price,
    val theaterName: String
) {
    fun calculateNotification(notificationPolicy: ReservationNotificationPolicy): LocalDateTime {
        return notificationPolicy.calculateTime(reservationDetail.date)
    }
}
