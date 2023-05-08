package woowacourse.movie.view.reservationresult

import woowacourse.movie.view.reservationresult.ReservationResultContract
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.model.mapper.TicketsMapper.toDomain

class ReservationResultPresenter(
    val view: ReservationResultContract.View,
    val ticketsUiModel: TicketsUiModel
) : ReservationResultContract.Presenter {
    override fun updatePrice() {
        val tickets = ticketsUiModel.toDomain()
        view.setPriceTextView(tickets.price.value)
    }
}
