package woowacourse.movie.data.model.mapper

import domain.Ticket
import domain.discountPolicy.DisCountPolicies
import woowacourse.movie.data.model.uimodel.TicketUiModel

object TicketMapper : DomainViewMapper<Ticket, TicketUiModel> {
    override fun toUi(domainModel: Ticket): TicketUiModel {
        return TicketUiModel(
            date = domainModel.date,
            seat = SeatMapper.toUi(domainModel.seat)
        )
    }

    override fun toDomain(ticketUiModel: TicketUiModel): Ticket {
        return Ticket(
            ticketUiModel.date,
            SeatMapper.toDomain(ticketUiModel.seat),
            DisCountPolicies()
        )
    }
}
