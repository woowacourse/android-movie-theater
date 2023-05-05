package woowacourse.movie.view.seatselection

import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.SeatUiModel

interface SeatSelectionContract {
    interface View {
        val presenter: Presenter

        fun getReservationOptions(): ReservationOptions?
        fun getMovie(): MovieListModel.MovieUiModel?
        fun initSeatButtons(rowRange: IntRange, colRange: IntRange)
        fun initReserveLayout(reservationOptions: ReservationOptions)
        fun disableReservation()
        fun findSelectedSeatsIndex(): List<Int>
        fun enableReservation(reservationFee: Int)
        fun registerReservationAlarm(reservation: ReservationUiModel)
    }

    interface Presenter {
        fun setUp()
        fun createSeat(row: Int, col: Int): SeatUiModel
        fun selectSeat(): Boolean
        fun deselectSeat()
        fun reserveSeats()
    }
}
