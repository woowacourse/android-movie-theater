package woowacourse.movie.presentation.view.main.booklist

import android.content.Context
import woowacourse.movie.data.database.MovieHelper
import woowacourse.movie.presentation.model.ReservationResult

class BookingListPresenter(
    private val view: BookingListContract.View,
    private val context: Context
) : BookingListContract.Presenter {
    override fun getReservationList() {
        val bookingEntity = MovieHelper(context).readBookingData()
        val booking = bookingEntity.map {
            ReservationResult.from(it)
        }
        view.showReservationListView(booking)
    }
}