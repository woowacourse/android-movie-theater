package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.ui.detail.adapter.SpinnerDateActionHandler
import woowacourse.movie.presentation.ui.detail.adapter.SpinnerTimeActionHandler

interface DetailContract {
    interface View : BaseView {
        fun showScreen(screen: Screen)

        fun showDateSpinnerAdapter(screenDates: List<ScreenDate>)

        fun showTimeSpinnerAdapter(screenDate: ScreenDate)

        fun showTicket(count: Int)

        fun navigateToSeatSelection(reservationInfo: ReservationInfo)

        fun navigateBackToPrevious()
    }

    interface Presenter : BasePresenter, SpinnerDateActionHandler, SpinnerTimeActionHandler {
        fun loadScreen(
            movieId: Int,
            theaterId: Int,
        )

        fun updateTicket(count: Int)

        fun plusTicket()

        fun minusTicket()

        fun selectSeat()

        fun loadDateSpinnerAdapter(screenDates: List<ScreenDate>)
    }
}
