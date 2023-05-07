package woowacourse.app.data.reservation

import java.time.LocalDateTime

data class ReservationEntity(
    val id: Long,
    val movieId: Long,
    val bookedDateTime: LocalDateTime,
    val paymentType: Int,
)
