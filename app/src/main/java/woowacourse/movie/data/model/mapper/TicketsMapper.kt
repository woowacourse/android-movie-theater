package woowacourse.movie.data.model.mapper
import domain.Tickets
import woowacourse.movie.data.model.uimodel.TicketsUIModel

object TicketsMapper : DomainViewMapper<Tickets, TicketsUIModel> {
    override fun toUI(domainModel: Tickets): TicketsUIModel {
        return TicketsUIModel(domainModel.list.map { TicketMapper.toUI(it) })
    }

    override fun toDomain(ticketsUIModel: TicketsUIModel): Tickets {
        return Tickets(ticketsUIModel.list.map { TicketMapper.toDomain(it) })
    }
}
