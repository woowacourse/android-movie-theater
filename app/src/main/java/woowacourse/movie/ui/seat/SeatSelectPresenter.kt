package woowacourse.movie.ui.seat

import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class SeatSelectPresenter(
    private val view: SeatSelectContract.View
) : SeatSelectContract.Presenter {
    private val discountApplyUseCase = DiscountApplyUseCase()

    override fun discountApply(tickets: TicketsState) {
        view.setMoneyText(discountApplyUseCase(tickets.asDomain()).asPresentation())
    }

    override fun addTicket(tickets: TicketsState) {
        TicketsRepository.addTicket(tickets)
        view.navigateToConfirmView(tickets)
    }
}
