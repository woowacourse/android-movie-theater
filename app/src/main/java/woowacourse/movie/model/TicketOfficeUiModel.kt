package woowacourse.movie.model

import java.io.Serializable
import java.time.LocalDateTime

class TicketOfficeUiModel(
    val ticketsUiModel: TicketsUiModel,
    val peopleCount: Int,
    val theaterName: String,
    val date: LocalDateTime
) : Serializable,
    UiModel
