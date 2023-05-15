package woowacourse.movie.view.activities.seatselection

interface SeatSelectionContract {
    interface Presenter {
        fun loadScreening()

        fun setSelectedSeats(seatNames: Set<String>)

        fun reserve(seatNames: Set<String>)
    }

    interface View {
        fun setSeats(seatUIStates: SeatsUIState)

        fun setMovieTitle(title: String)

        fun setReservationFee(fee: Int)

        fun setReservation(reservationId: Long)
    }
}
