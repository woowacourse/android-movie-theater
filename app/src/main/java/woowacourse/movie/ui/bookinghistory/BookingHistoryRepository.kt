package woowacourse.movie.ui.bookinghistory

import woowacourse.movie.model.ReservationUiModel

interface BookingHistoryRepository {

    fun loadBookingHistory(): List<ReservationUiModel>
}
