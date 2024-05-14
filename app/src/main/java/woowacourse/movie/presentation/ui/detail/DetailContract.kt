package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.model.ReservationInfo
import java.time.LocalDate
import java.time.LocalTime

interface DetailContract {
    interface View : BaseView {
        fun showScreen(screen: Screen)

        fun showTime(screenDate: ScreenDate)

        fun showTicket(count: Int)

        fun navigateToSeatSelection(reservationInfo: ReservationInfo)

        fun navigateBackToPrevious()
    }

    interface Presenter : BasePresenter {
        fun loadScreen(
            movieId: Int,
            theaterId: Int,
        )

        fun updateTicket(count: Int)

        fun plusTicket()

        fun minusTicket()

        fun selectSeat()

        fun selectDate(date: LocalDate)

        fun selectTime(time: LocalTime)
    }
}
