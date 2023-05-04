package woowacourse.movie.ui.bookinghistory

import woowacourse.movie.model.ReservationUiModel

interface BookingHistoryRepository {

    fun insertBookingHistory(reservationUiModel: ReservationUiModel)

    fun loadBookingHistory(): List<ReservationUiModel>
}
