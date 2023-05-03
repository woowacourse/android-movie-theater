package woowacourse.movie.ui.confirm

import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class ReservationConfirmPresenter(
    private val view: ReservationConfirmContract.View
) : ReservationConfirmContract.Presenter {
    private val discountApplyUseCase = DiscountApplyUseCase()

    override fun setDiscountApplyMoney(tickets: TicketsState) {
        val discountApplyMoney = discountApplyUseCase(tickets.asDomain())
        view.setMoneyTextView(discountApplyMoney.asPresentation())
    }
}
