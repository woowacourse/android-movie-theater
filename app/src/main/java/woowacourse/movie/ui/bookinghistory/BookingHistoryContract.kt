package woowacourse.movie.ui.bookinghistory

import woowacourse.movie.model.ReservationUiModel

interface BookingHistoryContract {

    interface View {

        fun initAdapter(bookingHistories: List<ReservationUiModel>)
    }

    interface Presenter {

        fun initBookingHistories()
    }
}
