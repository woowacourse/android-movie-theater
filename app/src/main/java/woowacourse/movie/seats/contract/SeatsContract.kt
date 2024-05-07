package woowacourse.movie.seats.contract

import woowacourse.movie.seats.model.Seats

interface SeatsContract {
    interface View {
        fun initClickListener()

        fun showMovieTitle(movieTitle: String)

        fun showSeats(seats: Seats)

        fun showTotalPrice(total: Int)

        fun updateConfirmButton(enabled: Boolean)

        fun moveToReservationResult(movieTicketId: Int)

        fun showMessage(message: String)
    }

    interface Presenter {
        fun loadScreeningInformation()

        fun onSeatClicked(seatIndex: Int)

        fun requestReservationResult()

        fun selectedSeats(): ArrayList<Int>
    }
}
