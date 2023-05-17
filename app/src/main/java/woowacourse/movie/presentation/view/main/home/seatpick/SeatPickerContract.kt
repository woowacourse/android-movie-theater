package woowacourse.movie.presentation.view.main.home.seatpick

import woowacourse.movie.presentation.model.ReservationResult
import woowacourse.movie.presentation.view.main.home.seatpick.model.SeatModel

interface SeatPickerContract {
    interface View {
        fun initView(movieTitle: String)
        fun finishErrorView()
        fun updateDefaultTotalPriceView(value: String)
        fun showTicketIsMaxCountView(ticketCount: Int)
        fun showConfirmDialog()
        fun updateNotification(reservationResult: ReservationResult, notificationTimeStamp: Long)
        fun showBookCompleteView(reservation: ReservationResult)
        fun updateTotalPriceView(totalPrice: String)
    }

    interface Presenter {
        fun onCreate()
        fun getDefaultTotalPrice()
        fun confirmBooking()
        fun bookComplete()
        fun setSeatInfo(seatIndex: Int): SeatModel
        fun calculateTotalPrice()
        fun updateSeatStatus(isSelected: Boolean, seatIndex: Int): Boolean
    }
}