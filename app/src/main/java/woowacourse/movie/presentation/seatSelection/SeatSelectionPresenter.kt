package woowacourse.movie.presentation.seatSelection

import woowacourse.movie.model.Movie
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.SeatingSystem
import woowacourse.movie.repository.DummyTheaterList
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.repository.TheaterListRepository

class SeatSelectionPresenter(
    private val seatSelectionContractView: SeatSelectionContract.View,
    ticketCount: Int,
    private val movieRepository: MovieRepository = MovieRepository(),
    private val theaterListRepository: TheaterListRepository = DummyTheaterList,
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
    ) {
        val reservation =
            Reservation(selectedMovie.title, screeningDateTime, seatingSystem.selectedSeats.toList(), findTheaterName(theaterId))
        seatSelectionContractView.navigate(reservation)
    }

    private fun findTheaterName(theaterId: Long): String = theaterListRepository.findTheaterOrNull(theaterId)?.name ?: ""
}
