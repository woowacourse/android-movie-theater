package woowacourse.movie.presentation.seatSelection

import android.content.Context
import android.util.Log
import woowacourse.movie.database.ReservationData
import woowacourse.movie.model.Movie
import woowacourse.movie.model.SeatingSystem
import woowacourse.movie.model.Ticket
import woowacourse.movie.notification.ReservationNotification
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.repository.ReservationRepository
import woowacourse.movie.repository.ReservationRepositoryImpl

class SeatSelectionPresenter(
    private val seatSelectionContractView: SeatSelectionContract.View,
    ticketCount: Int,
    private val movieRepository: MovieRepository = MovieRepository(),
) : SeatSelectionContract.Presenter {
    private lateinit var selectedMovie: Movie
    val seatingSystem = SeatingSystem(ticketCount)

    override fun loadMovieData(id: Long) {
        movieRepository.findMovieById(id)
            .onSuccess { movie ->
                selectedMovie = movie
                seatSelectionContractView.bindMovie(movie)
                updateUI()
            }
            .onFailure {
                seatSelectionContractView.showToastMessage(it.message)
            }
    }

    override fun loadSeats() {
        seatSelectionContractView.displaySeats(seatingSystem.seats)
        seatSelectionContractView.bindSeatingSystem(seatingSystem)
    }

    override fun updateSeatSelection(index: Int) {
        if (seatingSystem.isSelected(index)) {
            unSelectSeat(index)
        } else {
            selectSeat(index)
        }
    }

    private fun unSelectSeat(index: Int) {
        seatingSystem.unSelectSeat(index)
        seatSelectionContractView.updateUnSelectedSeatUI(index)
        updateUI()
    }

    private fun selectSeat(index: Int) {
        seatingSystem.trySelectSeat(index)
            .onSuccess {
                seatSelectionContractView.updateSelectedSeatUI(index)
                updateUI()
            }
            .onFailure {
                seatSelectionContractView.showToastMessage(it.message)
            }
    }

    private fun updateUI() {
        seatSelectionContractView.updateViews()
    }

    override fun navigate(
        screeningDateTime: String,
        theaterId: Long,
        context: Context,
    ) {
        val ticket =
            Ticket(
                selectedMovie.title,
                screeningDateTime,
                seatingSystem.selectedSeats.toList(),
                theaterId,
            )
        var ticketId = -1L
        // val t =
        //    Thread {
        ticketId = storeDataToDatabase(ReservationRepositoryImpl(context), ticket)
        //    }
        // t.start()
        // t.join()
        Log.d("seatSelection", "$ticketId")
        seatSelectionContractView.navigate(ticket, ticketId)
    }

    private fun storeDataToDatabase(
        reservationRepository: ReservationRepository,
        ticket: Ticket,
    ): Long {
        val reservationData: ReservationData =
            ReservationData(
                theaterId = ticket.theaterId,
                movieTitle = ticket.movieTitle,
                screenDate = ticket.screeningDateTime.split(' ')[0],
                screenTime = ticket.screeningDateTime.split(' ')[1],
                selectedSeats = ticket.selectedSeatsToString(),
                totalPrice = ticket.totalPriceToString(),
            )
        var ticketId: Long = -1L
        val t =
            Thread {
                ticketId = reservationRepository.saveReservationData(ticket)
            }
        t.start()
        t.join()
        Log.d("seatselection crong", "$ticketId")
        return ticketId
    }

    override fun setAlarm(
        context: Context,
        movieTitle: String,
        ticket: Ticket,
        ticketId: Long,
    ) {
        ReservationNotification.setNotification(
            context = context,
            movieTitle = movieTitle,
            screeningDateTime = ticket.screeningDateTime,
            ticketId = ticketId,
        )
    }
}
