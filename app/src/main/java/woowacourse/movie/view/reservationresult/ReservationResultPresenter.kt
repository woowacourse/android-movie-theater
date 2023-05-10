package woowacourse.movie.view.reservationresult

import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.model.mapper.TicketsMapper.toDomain

class ReservationResultPresenter(
    val view: ReservationResultContract.View,
) : ReservationResultContract.Presenter {
    override fun calculateTicketsPrice(ticketsUiModel: TicketsUiModel) {
        val tickets = ticketsUiModel.toDomain()
        view.setPriceText(tickets.price.value)
    }
}
