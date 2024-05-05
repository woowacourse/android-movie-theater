package woowacourse.movie.selectseat

import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.ReserveSeats
import woowacourse.movie.moviereservation.toHeadCount
import woowacourse.movie.moviereservation.uimodel.BookingInfoUiModel
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.selectseat.uimodel.PriceUiModel
import woowacourse.movie.selectseat.uimodel.SeatUiModel

class SelectSeatPresenter(
    private val view: SelectSeatContract.View,
    private val repository: MovieRepository,
) : SelectSeatContract.Presenter {
    private lateinit var movieTheater: MovieTheater

    override fun loadSeat(movieId: Long) {
        runCatching {
            movieTheater = repository.screenMovieById(movieId).theater
        }.onSuccess {
            view.showSeat(movieTheater.seats.toSeatsUiModel())
        }
    }

    override fun loadReservationInfo(movieId: Long) {
        runCatching {
            repository.screenMovieById(movieId)
        }.onSuccess {
            view.showMovieInfo(it.movie.title, PriceUiModel(ReserveSeats().totalPrice.price.toInt()))
        }
    }

    override fun completeReservation(bookingInfoUiModel: BookingInfoUiModel) {
        runCatching {
            repository.reserveMovie(
                bookingInfoUiModel.screenMovieId,
                bookingInfoUiModel.localDateTime(),
                bookingInfoUiModel.count.toHeadCount(),
                ReserveSeats(movieTheater.selectedSeats()),
                bookingInfoUiModel.theaterId,
            )
        }.onSuccess {
            view.navigateToResult(it)
        }
    }

    override fun changeSeatState(selectedSeat: SeatUiModel) {
        movieTheater = movieTheater.changeSeatState(selectedSeat.row, selectedSeat.col)
        val updatedPrice = ReserveSeats(movieTheater.selectedSeats()).totalPrice
        view.updatePrice(PriceUiModel(updatedPrice.price))
        view.updateSeatState(movieTheater.selectedSeats().toSeatsUiModel())
    }
}
