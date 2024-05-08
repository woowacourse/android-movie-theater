package woowacourse.movie.domain.repository

import java.time.LocalDateTime

interface NotificationRepository {
    fun createNotification(
        reservationId: Long,
        movieTitle: String,
        dateTime: LocalDateTime,
    ): Result<Unit>
}
