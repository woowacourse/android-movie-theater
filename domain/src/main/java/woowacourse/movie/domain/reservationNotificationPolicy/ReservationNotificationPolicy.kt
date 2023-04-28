package woowacourse.movie.domain.reservationNotificationPolicy

import java.time.LocalDateTime

interface ReservationNotificationPolicy {
    fun calculateTime(date: LocalDateTime): LocalDateTime
}
