package woowacourse.movie.presentation.view.main.home.bookcomplete

import woowacourse.movie.presentation.model.ReservationResult

interface BookingCompleteContract {
    interface View {
        fun initView(reservationResult: ReservationResult)
        fun finishErrorView()
    }

    interface Presenter {
        fun onCreate()
    }
}