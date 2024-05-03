package woowacourse.movie.selectseat

import woowacourse.movie.model.Seats
import woowacourse.movie.moviedetail.uimodel.BookingInfoUiModel
import woowacourse.movie.moviedetail.uimodel.toHeadCount
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.selectseat.uimodel.PriceUiModel
import woowacourse.movie.selectseat.uimodel.SeatUiModel
import woowacourse.movie.selectseat.uimodel.toSeats
import woowacourse.movie.selectseat.uimodel.toSeatsUiModel

class SelectSeatPresenter(
    private val view: SelectSeatContract.View,
    private val repository: MovieRepository,
) : SelectSeatContract.Presenter {
    override fun loadSeat(movieId: Long) {
        runCatching {
            repository.screeningById(movieId)
        }.onSuccess {
            view.showSeat(it.theater.seats().toSeatsUiModel())
        }
    }

    override fun loadReservationInfo(movieId: Long) {
        runCatching {
            repository.screeningById(movieId)
        }.onSuccess {
            view.showMovieInfo(it.movie.title, PriceUiModel(Seats().totalPrice.price.toInt()))
        }
    }

    override fun calculatePrice(selectedSeats: List<SeatUiModel>) {
        val updatedPrice = Seats(selectedSeats.toSeats()).totalPrice
        view.updatePrice(PriceUiModel(updatedPrice.price.toInt()))
    }

    override fun completeReservation(
        bookingInfoUiModel: BookingInfoUiModel,
        selectedSeats: List<SeatUiModel>,
    ) {
        runCatching {
            repository.makeReservation(
                bookingInfoUiModel.screenMovieId,
                bookingInfoUiModel.localDateTime(),
                bookingInfoUiModel.count.toHeadCount(),
                Seats(selectedSeats.toSeats()),
                bookingInfoUiModel.theaterId,
            )
        }.onSuccess {
            view.navigateToResult(it)
        }
    }
}
