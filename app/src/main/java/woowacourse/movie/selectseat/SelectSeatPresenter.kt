package woowacourse.movie.selectseat

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.SelectResult
import woowacourse.movie.model.SelectedSeats
import woowacourse.movie.moviereservation.uimodel.BookingInfo
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.selectseat.uimodel.SeatUiModel
import woowacourse.movie.selectseat.uimodel.SelectState

class SelectSeatPresenter(
    private val view: SelectSeatContract.View,
    private val repository: MovieRepository,
) : SelectSeatContract.Presenter {
    private lateinit var movieTheater: MovieTheater
    private lateinit var selectedSeats: SelectedSeats

    override fun loadSeat(
        movieId: Long,
        count: Int,
    ) {
        runCatching {
            movieTheater = repository.screenMovieById(movieId).theater
            selectedSeats = SelectedSeats(movieTheater, HeadCount(count))
            movieTheater
        }.onSuccess {
            view.showSeat(it.seats.toSeatsUiModel())
        }
    }

    override fun loadReservationInfo(movieId: Long) {
        runCatching {
            repository.screenMovieById(movieId)
        }.onSuccess {
            view.showMovieInfo(it.movie.title, selectedSeats.totalPrice.price)
        }
    }

    override fun changeSeatState(selectedSeat: SeatUiModel) {
        val result = this.selectedSeats.select(selectedSeat.row, selectedSeat.col)
        this.selectedSeats = result.value
        val selectedSeats = result.value.selectedSeats.toSeatsUiModel()

        when (result) {
            is SelectResult.AlreadyReserve -> view.updateSeatState(selectedSeats, SelectState.NONE)
            is SelectResult.Exceed -> view.updateSeatState(selectedSeats, SelectState.EXCEED)
            is SelectResult.LessSelect -> view.updateSeatState(selectedSeats, SelectState.LESS)
            is SelectResult.Success ->
                view.updateSeatState(selectedSeats, SelectState.SUCCESS)
        }

        view.updatePrice(this.selectedSeats.totalPrice.price)
    }

    override fun completeReservation(bookingInfoUiModel: BookingInfo) {
        runCatching {
            repository.reserveMovie(
                bookingInfoUiModel.screenMovieId,
                bookingInfoUiModel.dateTime,
                HeadCount(bookingInfoUiModel.count),
                selectedSeats,
                bookingInfoUiModel.theaterId,
            )
        }.onSuccess {
            view.navigateToResult(it)
        }
    }
}
