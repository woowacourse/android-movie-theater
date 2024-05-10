package woowacourse.movie.presentation.seats

import woowacourse.movie.domain.model.seat.Seats

interface SeatsContract {
    interface View {
        fun initClickListener()

        fun showMovieTitle(movieTitle: String)

        fun showSeats(seats: Seats)

        fun showTotalPrice(total: Int)

        fun updateConfirmButton(enabled: Boolean)

        fun moveToReservationResult(movieTicketId: Long)

        fun showMessage(message: String)
    }

    interface Presenter {
        fun loadScreeningInformation()

        fun onSeatClicked(seatIndex: Int)

        fun requestReservationResult()

        fun selectedSeats(): ArrayList<Int>
    }
}
