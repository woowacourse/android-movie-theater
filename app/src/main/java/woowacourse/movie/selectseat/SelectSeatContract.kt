package woowacourse.movie.selectseat

import woowacourse.movie.moviereservation.uimodel.BookingInfoUiModel
import woowacourse.movie.selectseat.uimodel.SeatUiModel
import woowacourse.movie.selectseat.uimodel.SelectState

interface SelectSeatContract {
    interface View {
        fun showSeat(theaterSeats: List<SeatUiModel>)

        fun showMovieInfo(
            title: String,
            price: Int,
        )

        fun updatePrice(updatedPrice: Int)

        fun updateSeatState(
            selectedSeats: List<SeatUiModel>,
            selectState: SelectState,
        )

        fun navigateToResult(reservationId: Long)
    }

    interface Presenter {
        fun loadSeat(
            movieId: Long,
            count: Int,
        )

        fun loadReservationInfo(movieId: Long)

        fun completeReservation(bookingInfoUiModel: BookingInfoUiModel)

        fun changeSeatState(selectedSeat: SeatUiModel)
    }
}
