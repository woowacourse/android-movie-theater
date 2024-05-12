package woowacourse.movie.feature.seat

import android.content.Context
import woowacourse.movie.data.notification.NotificationRepository
import woowacourse.movie.data.notification.NotificationSharedPreferencesRepository
import woowacourse.movie.data.reservation.dto.Reservation
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import java.lang.IllegalArgumentException
import kotlin.concurrent.thread

class MovieSeatSelectionPresenter(
    private val view: MovieSeatSelectionContract.View,
    applicationContext: Context,
) : MovieSeatSelectionContract.Presenter {
    private lateinit var movieSelectedSeats: MovieSelectedSeats
    private val notificationRepository: NotificationRepository
            by lazy { NotificationSharedPreferencesRepository.instance(applicationContext) }

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
        val ticket = savaTicket(ticketRepository, reservation, selectedSeats)
        view.navigateToResultView(ticket.id)
        if (notificationRepository.isGrant()) {
            view.setTicketAlarm(ticket)
        }
    }

    private fun savaTicket(
        ticketRepository: TicketRepository,
        reservation: Reservation,
        selectedSeats: MovieSelectedSeats,
    ): Ticket {
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
        return ticket ?: throw IllegalArgumentException()
    }
}
