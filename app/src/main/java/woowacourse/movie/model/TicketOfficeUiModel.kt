package woowacourse.movie.model

import java.io.Serializable

class TicketOfficeUiModel(val ticketsUiModel: TicketsUiModel, val ticketCount: Int) : Serializable,
    UiModel
