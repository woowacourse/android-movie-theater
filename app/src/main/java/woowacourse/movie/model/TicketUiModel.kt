package woowacourse.movie.model

import java.io.Serializable
import java.time.LocalDateTime

data class TicketUiModel(
    val date: LocalDateTime,
    val seat: SeatUiModel
) : UiModel, Serializable
