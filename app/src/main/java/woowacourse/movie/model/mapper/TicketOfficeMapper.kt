package woowacourse.movie.model.mapper

import domain.TicketOffice
import domain.discountPolicy.DisCountPolicies
import woowacourse.movie.model.TicketOfficeUiModel
import woowacourse.movie.model.mapper.TicketsMapper.toDomain
import woowacourse.movie.model.mapper.TicketsMapper.toUi

object TicketOfficeMapper : DomainViewMapper<TicketOffice, TicketOfficeUiModel> {
    override fun TicketOffice.toUi(): TicketOfficeUiModel {
        return TicketOfficeUiModel(
            date = date,
            peopleCount = peopleCount,
            theaterName = theaterName,
            ticketsUiModel = tickets.toUi()
        )
    }

    override fun TicketOfficeUiModel.toDomain(): TicketOffice {
        return TicketOffice(
            date = date,
            peopleCount = peopleCount,
            theaterName = theaterName,
            tickets = ticketsUiModel.toDomain(),
            disCountPolicies = DisCountPolicies()
        )
    }
}
