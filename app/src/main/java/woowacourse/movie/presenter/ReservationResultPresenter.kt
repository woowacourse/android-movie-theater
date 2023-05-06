package woowacourse.movie.presenter

import woowacourse.movie.R
import woowacourse.movie.contract.ReservationResultContract
import woowacourse.movie.model.TicketsUiModel
import woowacourse.movie.model.mapper.TicketsMapper.toDomain
import java.text.NumberFormat
import java.util.*

class ReservationResultPresenter(
    val view: ReservationResultContract.View,
    val ticketsUiModel: TicketsUiModel
) :
    ReservationResultContract.Presenter {
    override fun updatePrice() {
        val tickets = ticketsUiModel.toDomain()
        view.setPriceTextView(tickets.price.value)
    }
}
