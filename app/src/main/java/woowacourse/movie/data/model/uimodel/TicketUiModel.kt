package woowacourse.movie.data.model.uimodel

import java.io.Serializable
import java.time.LocalDateTime

data class TicketUiModel(
    val date: LocalDateTime,
    val seat: SeatUiModel,
    val theater: TheaterUiModel,
) : UiModel, Serializable
