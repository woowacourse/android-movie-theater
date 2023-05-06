package woowacourse.movie.data.model.uimodel

import java.io.Serializable

class TicketOfficeUiModel(val ticketsUiModel: TicketsUiModel, val ticketCount: Int) :
    Serializable,
    UiModel
