package woowacourse.movie.seatselection.presenter

import woowacourse.MovieApplication.Companion.database
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.seatselection.presenter.contract.MovieSeatSelectionContract
import woowacourse.movie.util.Formatter.formatColumn
import woowacourse.movie.util.Formatter.formatRow

class MovieSeatSelectionPresenter(
    private val movieSeatSelectionContractView: MovieSeatSelectionContract.View,
) : MovieSeatSelectionContract.Presenter {
    lateinit var movieSelectedSeats: MovieSelectedSeats

    override fun loadDetailMovie(id: Long) {
        val movieData = getMovieById(id)
        movieData?.let { movie ->
            movieSeatSelectionContractView.displayMovieTitle(movie.title)
        }
        movieSelectedSeats = MovieSelectedSeats()
        movieSeatSelectionContractView.updateSelectedSeats(movieSelectedSeats)
    }

    override fun loadTableSeats(count: Int) {
        movieSelectedSeats = MovieSelectedSeats(count)
        movieSeatSelectionContractView.setUpTableSeats(movieSelectedSeats.getBaseSeats())
    }

    override fun updateSelectedSeats(count: Int) {
        movieSelectedSeats = MovieSelectedSeats(count)
    }

    override fun clickTableSeat(index: Int) {
        val seat = movieSelectedSeats.getBaseSeats()[index]
        if (movieSelectedSeats.isSelected(index)) {
            movieSeatSelectionContractView.updateSeatBackgroundColor(index, true)
            movieSelectedSeats.unSelectSeat(seat)
        } else {
            if (!movieSelectedSeats.isSelectionComplete()) {
                movieSeatSelectionContractView.updateSeatBackgroundColor(index, false)
                movieSelectedSeats.selectSeat(seat)
            }
        }
        movieSeatSelectionContractView.updateSelectedSeats(movieSelectedSeats)
    }

    override fun clickPositiveButton(
        movieId: Long,
        date: String,
        time: String,
        theaterPosition: Int,
    ) {
        val count = movieSelectedSeats.count
        val seats =
            movieSelectedSeats.selectedSeats.joinToString(", ") { seat ->
                (seat.row.formatRow() + seat.column.formatColumn())
            }
        val ticketId = saveReservationHistory(date, time, count, seats, movieId, theaterPosition)
        movieSeatSelectionContractView.navigateToResultView(ticketId)
    }

    private fun saveReservationHistory(
        date: String,
        time: String,
        count: Int,
        seats: String,
        movieId: Long,
        theaterPosition: Int,
    ): Long {
        var ticketId = -1L
        val reservationHistoryEntity =
            ReservationHistoryEntity(date, time, count, seats, movieId, theaterPosition)
        val thread =
            Thread {
                ticketId =
                    database.reservationHistoryDao()
                        .saveReservationHistory(reservationHistoryEntity)
            }
        thread.start()
        thread.join()

        return ticketId
    }
}
