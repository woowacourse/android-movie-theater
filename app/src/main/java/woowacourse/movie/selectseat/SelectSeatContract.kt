package woowacourse.movie.selectseat

import woowacourse.movie.model.HeadCount
import woowacourse.movie.selectseat.uimodel.Position
import woowacourse.movie.selectseat.uimodel.PriceUiModel
import woowacourse.movie.selectseat.uimodel.SeatUiModel

interface SelectSeatContract {
    interface View {
        fun initSeats(seatUiModels: Map<Position, SeatUiModel>)

        fun showMovieInfo(
            title: String,
            priceUiModel: PriceUiModel,
        )

        fun activatePurchase()

        fun deActivatePurchase()

        fun showSeats(seatUiModels: Map<Position, SeatUiModel>)

        fun showPrice(updatedPrice: PriceUiModel)

        fun onSaveSeats(seatUiModels: Map<Position, SeatUiModel>)

        fun navigateToResult(reservationId: Long)
    }

    interface Presenter {
        fun initSeats(screeningId: Long)

        fun initMaxCount(headCount: HeadCount)

        fun selectSeat(position: Position)

        fun loadReservationInfo(movieId: Long)

        fun saveSeats()

        fun loadSeats(seats: Map<Position, SeatUiModel>)

        fun completeReservation()
    }
}
