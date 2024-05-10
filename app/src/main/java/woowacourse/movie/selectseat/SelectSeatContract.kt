package woowacourse.movie.selectseat

import woowacourse.movie.moviereservation.uimodel.BookingInfo
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
        fun loadInitData(
            movieId: Long,
            count: Int,
        )

        fun completeReservation(bookingInfoUiModel: BookingInfo)

        fun changeSeatState(selectedSeat: SeatUiModel)
    }
}
