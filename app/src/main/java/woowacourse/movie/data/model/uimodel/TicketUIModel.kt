package woowacourse.movie.data.model.uimodel

import java.io.Serializable
import java.time.LocalDateTime

data class TicketUIModel(
    val date: LocalDateTime,
    val seat: SeatUIModel,
    val theater: TheaterUIModel,
) : UIModel, Serializable
