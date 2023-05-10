package woowacourse.movie.model.mapper
import domain.Tickets
import woowacourse.movie.model.mapper.TicketMapper.toDomain
import woowacourse.movie.model.mapper.TicketMapper.toUi
import woowacourse.movie.model.TicketsUiModel

object TicketsMapper : DomainViewMapper<Tickets, TicketsUiModel> {
    override fun Tickets.toUi(): TicketsUiModel {
        return TicketsUiModel(list.map { it.toUi() })
    }

    override fun TicketsUiModel.toDomain(): Tickets {
        return Tickets(list.map { it.toDomain() })
    }
}
