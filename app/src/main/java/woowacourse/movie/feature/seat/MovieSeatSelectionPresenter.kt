package woowacourse.movie.feature.seat

import woowacourse.movie.data.reservation.Reservation
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import java.lang.IllegalArgumentException
import kotlin.concurrent.thread

class MovieSeatSelectionPresenter(
    private val view: MovieSeatSelectionContract.View,
) : MovieSeatSelectionContract.Presenter {
    private lateinit var movieSelectedSeats: MovieSelectedSeats

    override fun loadReservation(
        reservationRepository: ReservationRepository,
        reservationId: Long,
    ) {
        runCatching { reservationRepository.find(reservationId) }
            .onSuccess { view.setUpReservation(it) }
            .onFailure { view.showToastInvalidMovieIdError(it) }
    }

    override fun loadTableSeats(movieSelectedSeats: MovieSelectedSeats) {
        this.movieSelectedSeats = movieSelectedSeats
        view.setUpTableSeats(movieSelectedSeats.getBaseSeats())
    }

    override fun updateSelectedSeats(movieSelectedSeats: MovieSelectedSeats) {
        this.movieSelectedSeats = movieSelectedSeats
    }

    override fun selectSeat(index: Int) {
        val seat = movieSelectedSeats.getBaseSeats()[index]
        when {
            movieSelectedSeats.isSelected(index) -> unSelectSeat(seat, index)
            !movieSelectedSeats.isSelectionComplete() -> selectSeat(seat, index)
        }
        view.updateSelectResult(movieSelectedSeats)
    }

    private fun selectSeat(
        seat: MovieSeat,
        index: Int,
    ) {
        view.updateSeatBackgroundColor(index, false)
        movieSelectedSeats.selectSeat(seat)
    }

    private fun unSelectSeat(
        seat: MovieSeat,
        index: Int,
    ) {
        view.updateSeatBackgroundColor(index, true)
        movieSelectedSeats.unSelectSeat(seat)
    }

    override fun reserveMovie(
        ticketRepository: TicketRepository,
        reservation: Reservation,
        selectedSeats: MovieSelectedSeats,
    ) {
        var ticket: Ticket? = null
        thread {
            val ticketId =
                ticketRepository.save(
                    reservation.movieId,
                    reservation.screeningDate,
                    reservation.screeningTime,
                    selectedSeats,
                    reservation.theaterName,
                )
            ticket = ticketRepository.find(ticketId)
        }.join()
        ticket?.let {
            view.navigateToResultView(it.id)
            view.setTicketAlarm(it)
        } ?: throw IllegalArgumentException()
    }
}
