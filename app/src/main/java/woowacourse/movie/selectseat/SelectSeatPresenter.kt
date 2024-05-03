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
            repository.screeningById(movieId) ?: error(SCREENING_NOT_EXISTS_ERROR)
        }.onSuccess {
            view.showSeat(it.theater.seats.toSeatsUiModel())
        }
    }

    override fun loadReservationInfo(movieId: Long) {
        runCatching {
            repository.screeningById(movieId) ?: error(SCREENING_NOT_EXISTS_ERROR)
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
            val screening =
                repository.screeningById(bookingInfoUiModel.screenMovieId) ?: error(
                    SCREENING_NOT_EXISTS_ERROR,
                )
            repository.makeReservation(
                screening,
                bookingInfoUiModel.localDateTime(),
                bookingInfoUiModel.count.toHeadCount(),
                Seats(selectedSeats.toSeats()),
                bookingInfoUiModel.theaterId,
            )
        }.onSuccess {
            view.navigateToResult(it)
        }
    }

    companion object {
        private const val SCREENING_NOT_EXISTS_ERROR = "상영 정보를 찾을 수 없었습니다"
    }
}
