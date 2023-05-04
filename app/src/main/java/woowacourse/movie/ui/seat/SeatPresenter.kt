package woowacourse.movie.ui.seat

import woowacourse.movie.SelectResult
import woowacourse.movie.SelectedSeats
import woowacourse.movie.model.BookedMovie
import woowacourse.movie.model.Mapper.toUiModel
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieRepository
import woowacourse.movie.reservation.Reservation
import woowacourse.movie.theater.Theater
import woowacourse.movie.theater.TheaterRepository
import woowacourse.movie.ticket.Position
import woowacourse.movie.ticket.Ticket
import woowacourse.movie.ui.bookinghistory.BookingHistoryRepository

class SeatPresenter(
    private val view: SeatContract.View,
    private val repository: BookingHistoryRepository,
    private val timeReminder: TimeReminder,
    private val bookedMovie: BookedMovie,
) : SeatContract.Presenter {

    override val movie: Movie = MovieRepository.getMovie(bookedMovie.movieId)
    override val theater: Theater = TheaterRepository.getTheater(bookedMovie.theaterId)
    override val selectedSeats: SelectedSeats = SelectedSeats(bookedMovie.ticketCount)

    override fun initMovieTitle() {
        view.initMovieTitleText(movie.title)
    }

    override fun initSelectedSeats() {
        view.initSeatTableView(theater.rowSize, theater.columnSize)
    }

    override fun onSeatSelected(
        row: Int,
        col: Int,
    ) {
        val seat = theater.selectSeat(
            position = Position(row, col)
        )

        when (selectedSeats.clickSeat(seat)) {
            SelectResult.Select.Full -> view.showCannotSelectSeat()
            SelectResult.Select.Success -> view.setSeatSelected(row, col)
            SelectResult.Unselect -> view.setSeatSelected(row, col)
        }
        view.setButtonState(selectedSeats.isSeatFull)
        view.setSeatPayment(selectedSeats.payment)
    }

    override fun addReservation(reservationUiModel: ReservationUiModel) {
        timeReminder.remind(reservationUiModel)
        repository.insertBookingHistory(reservationUiModel)
    }

    override fun createReservation(): ReservationUiModel {
        val tickets: List<Ticket> = selectedSeats.seats.map {
            movie.reserve(bookedMovie.bookedDateTime, it)
        }

        return Reservation(tickets.toSet()).toUiModel()
    }
}
