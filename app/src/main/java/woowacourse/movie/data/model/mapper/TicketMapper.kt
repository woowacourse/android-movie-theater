package woowacourse.movie.data.model.mapper

import domain.Ticket
import domain.discountPolicy.DisCountPolicies
import woowacourse.movie.data.model.uimodel.TicketUIModel

object TicketMapper : DomainViewMapper<Ticket, TicketUIModel> {
    override fun toUI(domainModel: Ticket): TicketUIModel {
        return TicketUIModel(
            date = domainModel.date,
            seat = SeatMapper.toUI(domainModel.seat),
            theater = TheaterMapper.toUI(domainModel.theater)
        )
    }

    override fun toDomain(ticketUIModel: TicketUIModel): Ticket {
        return Ticket(
            ticketUIModel.date,
            SeatMapper.toDomain(ticketUIModel.seat),
            DisCountPolicies(),
            TheaterMapper.toDomain(ticketUIModel.theater)
        )
    }
}
