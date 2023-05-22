package woowacourse.movie.data.model.mapper

import domain.TicketOffice
import woowacourse.movie.data.model.uimodel.TicketOfficeUIModel

object TicketOfficeMapper : DomainViewMapper<TicketOffice, TicketOfficeUIModel> {
    override fun toDomain(ticketOfficeUIModel: TicketOfficeUIModel): TicketOffice {
        return TicketOffice(
            tickets = TicketsMapper.toDomain(ticketOfficeUIModel.ticketsUiModel),
            peopleCount = ticketOfficeUIModel.ticketCount
        )
    }

    override fun toUI(domainModel: TicketOffice): TicketOfficeUIModel {
        return TicketOfficeUIModel(
            ticketsUiModel = TicketsMapper.toUI(domainModel.tickets),
            ticketCount = domainModel.peopleCount
        )
    }
}
