package woowacourse.movie.ui.confirm

import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class ReservationConfirmPresenter(
    private val view: ReservationConfirmContract.View,
    private val ticket: TicketsState
) : ReservationConfirmContract.Presenter {
    private val discountApplyUseCase = DiscountApplyUseCase()

    override fun setUpTicket() {
        view.showTicket(ticket)
        view.registerNotification(ticket)
    }

    override fun discountApplyMoney(ticket: TicketsState) {
        val discountApplyMoney = discountApplyUseCase(ticket.asDomain())
        view.showMoney(discountApplyMoney.asPresentation())
    }
}
