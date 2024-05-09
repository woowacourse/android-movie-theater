package woowacourse.movie.presentation.seatSelection

import android.content.Context
import woowacourse.movie.database.ReservationData
import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.model.Movie
import woowacourse.movie.model.SeatingSystem
import woowacourse.movie.model.Ticket
import woowacourse.movie.repository.MovieRepository

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
        // Is it possible to erase context here?
        context: Context,
    ) {
        val ticket =
            Ticket(
                selectedMovie.title,
                screeningDateTime,
                seatingSystem.selectedSeats.toList(),
                theaterId,
            )
        Thread {
            ReservationDatabase.getInstance(context)?.let { storeDataToDatabase(it, ticket) }
        }.start()
        seatSelectionContractView.navigate(ticket)
    }

    private fun storeDataToDatabase(
        reservationDatabase: ReservationDatabase,
        ticket: Ticket,
    ) {
        val dao = reservationDatabase.reservationDao()
        val reservationData: ReservationData =
            ReservationData(
                theaterId = ticket.theaterId,
                movieTitle = ticket.movieTitle,
                screenDate = ticket.screeningDateTime.split(' ')[0],
                screenTime = ticket.screeningDateTime.split(' ')[1],
                selectedSeats = ticket.selectedSeatsToString(),
                totalPrice = ticket.totalPriceToString(),
            )
        dao.insertAll(reservationData)
    }
}
