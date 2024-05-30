package woowacourse.movie.data.repository

import java.time.LocalDateTime

interface NotificationRepository {
    fun register(
        id: Int,
        dateTime: LocalDateTime,
    )
}
