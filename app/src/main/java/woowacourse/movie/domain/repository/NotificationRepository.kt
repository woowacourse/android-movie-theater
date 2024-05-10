package woowacourse.movie.domain.repository

import java.time.LocalDateTime

interface NotificationRepository {
    fun registerNotification(
        reservationId: Long,
        movieTitle: String,
        dateTime: LocalDateTime,
    ): Result<Unit>
}
