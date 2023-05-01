package woowacourse.movie.view.mapper

import domain.Ticket
import domain.discountPolicy.DisCountPolicies
import woowacourse.movie.view.mapper.SeatMapper.toDomain
import woowacourse.movie.view.mapper.SeatMapper.toUi
import woowacourse.movie.view.model.TicketUiModel

object TicketMapper : DomainViewMapper<Ticket, TicketUiModel> {
    override fun Ticket.toUi(): TicketUiModel {
        return TicketUiModel(
            date = date,
            seat = seat.toUi()
        )
    }

    override fun TicketUiModel.toDomain(): Ticket {
        return Ticket(
            date,
            seat.toDomain(),
            DisCountPolicies()
        )
    }
}
