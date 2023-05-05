package woowacourse.movie.presentation.view.main.booklist

import woowacourse.movie.presentation.model.ReservationResult

interface BookingListContract {
    interface View {
        fun showReservationListView(bookings: List<ReservationResult>)
    }

    interface Presenter {
        fun getReservationList()
    }

}