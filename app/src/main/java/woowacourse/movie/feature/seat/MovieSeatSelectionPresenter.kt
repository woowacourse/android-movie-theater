package woowacourse.movie.feature.seat

import woowacourse.movie.data.movie.MovieRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import java.time.LocalDate
import java.time.LocalTime

class MovieSeatSelectionPresenter(
    private val view: MovieSeatSelectionContract.View,
) : MovieSeatSelectionContract.Presenter {
    private lateinit var movieSelectedSeats: MovieSelectedSeats

    override fun loadMovieTitle(movieId: Long) {
        val movie =
            runCatching {
                MovieRepository.getMovieById(movieId)
            }.getOrElse {
                view.showToastInvalidMovieIdError(it)
                return
            }

        view.displayMovieTitle(movie.title)
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
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        selectedSeats: MovieSelectedSeats,
        theaterName: String,
    ) {
        Thread {
            val ticketId =
                ticketRepository.save(
                    movieId,
                    screeningDate,
                    screeningTime,
                    selectedSeats,
                    theaterName,
                )
            view.navigateToResultView(ticketId)
            view.setTicketAlarm(ticketRepository.find(ticketId))
        }.start()
    }
}
