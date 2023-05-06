package woowacourse.movie.feature.reservation.confirm

import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class TicketsConfirmPresenter(
    val view: TicketsConfirmContract.View
) : TicketsConfirmContract.Presenter {

    override fun setDiscountApplyMoney(tickets: TicketsState) {
        val discountApplyUseCase = DiscountApplyUseCase()
        val discountApplyMoney = discountApplyUseCase(tickets.asDomain())
        view.setMoney(discountApplyMoney.asPresentation().price)
    }
}
