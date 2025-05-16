package woowacourse.movie.presentation.common.model

import woowacourse.movie.domain.model.reservation.ReservationHistory

data class ReservationHistoryUiModel(
    val id: Long,
    val ticket: TicketUiModel
)

fun ReservationHistory.toUiModel() = ReservationHistoryUiModel(id, ticket.toUiModel())
