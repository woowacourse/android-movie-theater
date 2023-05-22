package woowacourse.movie.data.model.uimodel

import java.io.Serializable

class TicketOfficeUIModel(val ticketsUiModel: TicketsUIModel, val ticketCount: Int) :
    Serializable,
    UIModel
