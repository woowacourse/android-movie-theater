package woowacourse.movie.presentation.seats

import woowacourse.movie.data.SampleMovieData
import woowacourse.movie.data.SampleSeatData
import woowacourse.movie.data.SampleTheaterData
import woowacourse.movie.domain.model.detail.DetailDataResource
import woowacourse.movie.domain.model.home.Movie
import woowacourse.movie.domain.model.home.Theater
import woowacourse.movie.domain.model.seat.SeatSelectionResult
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.domain.model.seat.SelectedSeat
import woowacourse.movie.domain.repository.MovieTicketRepository

class SeatsPresenter(
    private val view: SeatsContract.View,
    private val movieTicketRepository: MovieTicketRepository,
    theaterId: Long,
    private val movieId: Long,
    private val screeningDate: String,
    private val screeningTime: String,
    private val reservationCount: Int,
) : SeatsContract.Presenter {
    private lateinit var seats: Seats
    lateinit var movie: Movie
    private lateinit var theater: Theater

    init {
        if (movieId <= INVALID_ID || theaterId <= INVALID_ID || reservationCount <= 0) {
            view.showMessage(ERROR_MESSAGE.format(INVALID_MOVIE_TICKET_ID_MESSAGE))
        } else {
            loadScreeningInformation()
        }
    }

    override fun loadScreeningInformation() {
        runCatching {
            SampleMovieData.movieList.first { it.id == movieId }
        }.onSuccess {
            movie = it
            view.initClickListener()
            view.showMovieTitle(movie.title)
            setUpSeats(reservationCount)
        }.onFailure {
            view.showMessage(ERROR_MESSAGE.format(it.message ?: ""))
        }
    }

    private fun setUpSeats(reservationCount: Int) {
        val seatsBoard = SampleSeatData.seatsBoard
        val selectedSeats = seatsBoard.map { SelectedSeat(it) }
        seats = Seats(selectedSeats, reservationCount)
        view.showSeats(seats)
    }

    override fun onSeatClicked(seatIndex: Int) {
        val result = seats.toggleSeatSelection(seatIndex)
        when (result) {
            is SeatSelectionResult.Success -> {
                view.showSeats(seats)
                updateTotalPriceDisplay()
                view.updateConfirmButton(false)
            }

            is SeatSelectionResult.MaxCapacityReached -> {
                view.showSeats(seats)
                updateTotalPriceDisplay()
                view.updateConfirmButton(true)
            }

            is SeatSelectionResult.AlreadyMaxCapacityReached -> {
                view.showMessage(MAX_SELECTABLE_SEATS_EXCEEDED_MESSAGE)
                view.updateConfirmButton(true)
            }

            is SeatSelectionResult.Failure -> {
                view.showMessage(ERROR_SELECTING_SEAT_MESSAGE)
                view.updateConfirmButton(false)
            }
        }
    }

    override fun requestReservationResult() {
        runCatching {
            theater = SampleTheaterData.theaters.first { it.id == DetailDataResource.theaterId }
            movieTicketRepository.createMovieTicket(
                theater.name,
                movie.title,
                screeningDate,
                screeningTime,
                reservationCount,
                seats.toString(),
                seats.totalPrice()
            )
        }.onSuccess {
            view.moveToReservationResult(it.id)
        }.onFailure {
            view.showMessage(ERROR_MESSAGE.format(it.message ?: ""))
        }
    }

    override fun selectedSeats(): ArrayList<Int> {
        return seats.selectedSeatIndices().toCollection(ArrayList())
    }

    private fun updateTotalPriceDisplay() {
        val total = seats.totalPrice()
        view.showTotalPrice(total)
    }

    companion object {
        const val INVALID_ID = -1L
        const val INVALID_MOVIE_TICKET_ID_MESSAGE = "올바르지 않은 예매 정보입니다."
        const val ERROR_MESSAGE = "예매 정보를 불러오는데 실패했습니다. %s"
        const val MAX_SELECTABLE_SEATS_EXCEEDED_MESSAGE = "최대 선택 가능 좌석 수를 초과하였습니다."
        const val ERROR_SELECTING_SEAT_MESSAGE = "좌석 선택에 실패했습니다."
    }
}
