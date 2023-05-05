package woowacourse.movie.ui.confirm

import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class ReservationConfirmPresenter(
    private val view: ReservationConfirmContract.View
) : ReservationConfirmContract.Presenter {
    private val discountApplyUseCase = DiscountApplyUseCase()
    lateinit var ticket: TicketsState

    override fun init(ticket: TicketsState) {
        this.ticket = ticket
        view.setTicket(ticket)
        view.registerNotification(ticket)
    }

    override fun discountApplyMoney(ticket: TicketsState) {
        val discountApplyMoney = discountApplyUseCase(ticket.asDomain())
        view.setMoneyTextView(discountApplyMoney.asPresentation())
    }
}
