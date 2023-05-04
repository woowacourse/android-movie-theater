package woowacourse.movie.presentation.view.main.home.seatpick

import woowacourse.movie.presentation.model.ReservationResult
import woowacourse.movie.presentation.view.main.home.seatpick.model.SeatModel

interface SeatPickerContract {
    interface View {
        fun initView(movieTitle: String)
        fun finishErrorView()
        fun updateDefaultTotalPriceView(value: Int)
        fun showTicketIsMaxCountView(ticketCount: Int)
        fun showConfirmDialog()
        fun updateNotification(reservationResult: ReservationResult, notificationTimeStamp: Long)
        fun showBookCompleteView(reservationId: Long)
        fun updateTotalPriceView(totalPrice: Int)
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