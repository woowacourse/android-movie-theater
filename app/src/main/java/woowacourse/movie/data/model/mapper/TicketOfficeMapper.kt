package woowacourse.movie.data.model.mapper

import domain.TicketOffice
import woowacourse.movie.data.model.uimodel.TicketOfficeUiModel

object TicketOfficeMapper : DomainViewMapper<TicketOffice, TicketOfficeUiModel> {
    override fun toDomain(ticketOfficeUiModel: TicketOfficeUiModel): TicketOffice {
        return TicketOffice(
            tickets = TicketsMapper.toDomain(ticketOfficeUiModel.ticketsUiModel),
            peopleCount = ticketOfficeUiModel.ticketCount
        )
    }

    override fun toUi(domainModel: TicketOffice): TicketOfficeUiModel {
        return TicketOfficeUiModel(
            ticketsUiModel = TicketsMapper.toUi(domainModel.tickets),
            ticketCount = domainModel.peopleCount
        )
    }
}
