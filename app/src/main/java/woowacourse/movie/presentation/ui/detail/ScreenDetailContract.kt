package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.model.Quantity

interface ScreenDetailContract {
    interface View : BaseView, ScreenDetailActionHandler {
        fun showScreenDetail(screenDetail: ScreenDetailUiModel)

        fun updateTicketQuantity(quantity: Quantity)

        fun terminateOnError(e: Throwable)
    }

    interface Presenter {
        fun loadScreenDetail(
            movieId: Int,
            theaterId: Int,
        )

        fun increaseQuantity(quantity: Quantity)

        fun decreaseQuantity(quantity: Quantity)
    }
}
