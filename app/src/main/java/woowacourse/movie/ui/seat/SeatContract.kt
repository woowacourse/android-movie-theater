package woowacourse.movie.ui.seat

import woowacourse.movie.SelectedSeats
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.movie.Movie
import woowacourse.movie.theater.Theater

interface SeatContract {

    interface View {

        fun initMovieTitleText(movieTitle: String)
        fun showCannotSelectSeat()
        fun initSeatTableView(rowSize: Int, columnSize: Int)
        fun initSeatSelectListener()
        fun setSeatSelected(row: Int, col: Int)
        fun setSeatPayment(payment: Int)
        fun setButtonState(enabled: Boolean)
        fun showSeatReservationDialog(reservation: ReservationUiModel)
    }

    interface Presenter {

        val movie: Movie
        val theater: Theater
        val selectedSeats: SelectedSeats

        fun initMovieTitle()
        fun initSelectedSeats()
        fun onSeatSelected(row: Int, col: Int)
        fun addReservation(reservationUiModel: ReservationUiModel)
        fun createReservation(): ReservationUiModel
        fun onSeatReservationCompleted()
    }
}
