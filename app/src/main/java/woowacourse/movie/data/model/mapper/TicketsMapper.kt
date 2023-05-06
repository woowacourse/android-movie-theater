package woowacourse.movie.data.model.mapper
import domain.Tickets
import woowacourse.movie.data.model.uimodel.TicketsUiModel

object TicketsMapper : DomainViewMapper<Tickets, TicketsUiModel> {
    override fun toUi(domainModel: Tickets): TicketsUiModel {
        return TicketsUiModel(domainModel.list.map { TicketMapper.toUi(it) })
    }

    override fun toDomain(ticketsUiModel: TicketsUiModel): Tickets {
        return Tickets(ticketsUiModel.list.map { TicketMapper.toDomain(it) })
    }
}
