package woowacourse.movie.view.mapper
import domain.Tickets
import woowacourse.movie.view.mapper.TicketMapper.toDomain
import woowacourse.movie.view.mapper.TicketMapper.toUi
import woowacourse.movie.view.model.TicketsUiModel

object TicketsMapper : DomainViewMapper<Tickets, TicketsUiModel> {
    override fun Tickets.toUi(): TicketsUiModel {
        return TicketsUiModel(list.map { it.toUi() })
    }

    override fun TicketsUiModel.toDomain(): Tickets {
        return Tickets(list.map { it.toDomain() })
    }
}
