package woowacourse.movie.ui.notification

import java.time.LocalDateTime

data class ReservationAlarmItem(
    val reservationId: Long,
    val screeningStartDateTime: LocalDateTime,
    val title: String,
    val subTitle: String,
)
