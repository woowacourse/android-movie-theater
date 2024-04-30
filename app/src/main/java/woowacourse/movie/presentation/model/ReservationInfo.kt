package woowacourse.movie.presentation.model

import java.io.Serializable
import java.time.LocalDateTime

data class ReservationInfo(
    val theaterId: Int,
    val dateTime: LocalDateTime,
    val ticketCount: Int,
) : Serializable
