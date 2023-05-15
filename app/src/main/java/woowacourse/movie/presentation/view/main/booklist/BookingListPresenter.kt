package woowacourse.movie.presentation.view.main.booklist

import woowacourse.movie.data.database.MovieHelper
import woowacourse.movie.presentation.model.ReservationResult

class BookingListPresenter(
    private val view: BookingListContract.View,
    private val movieHelper: MovieHelper
) : BookingListContract.Presenter {
    override fun getReservationList() {
        val bookingEntity = movieHelper.readBookingData()
        val booking = bookingEntity.map {
            ReservationResult.from(it)
        }
        view.showReservationListView(booking)
    }
}