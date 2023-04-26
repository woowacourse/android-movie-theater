package woowacourse.movie.domain.reservationNotificationPolicy

import java.time.LocalDateTime

object MovieReservationNotification : ReservationNotificationPolicy {
    private const val NOTIFICATION_MINUTE: Long = 30

    override fun calculateTime(date: LocalDateTime): LocalDateTime {
        return date.minusMinutes(NOTIFICATION_MINUTE)
    }
}
