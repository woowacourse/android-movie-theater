package woowacourse.movie.presentation.seats

import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.seat.SelectedSeats

interface SeatsContract {
    interface View {
        fun initSeats()

        fun showMovieTitle(title: String)

        fun showConfirmDialog()

        fun showMessage(message: String)

        fun updateAmount(amount: Int)

        fun updateSelectedSeat(seat: Seat, isSelected: Boolean)

        fun updateConfirmButtonEnabled(canConfirm: Boolean)

        fun navigateToSummary(ticket: MovieTicket)
    }

    interface Presenter {
        fun initializeSeats(movieTicket: MovieTicket)

        fun selectSeat(seat: Seat)

        fun publishMovieTicket()

        fun restoreSeats(selectedSeats: SelectedSeats?)
    }
}
