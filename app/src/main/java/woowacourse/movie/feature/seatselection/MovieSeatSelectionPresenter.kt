package woowacourse.movie.feature.seatselection

import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.data.TicketRepository
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

    override fun clickTableSeat(index: Int) {
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

    override fun clickPositiveButton(
        ticketRepository: TicketRepository,
        movieId: Long,
        screeningDate: String,
        screeningTime: String,
        selectedSeats: MovieSelectedSeats,
        theaterName: String,
    ) {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val ticketId =
                ticketRepository.save(
                    movieId,
                    LocalDate.parse(screeningDate),
                    LocalTime.parse(screeningTime),
                    selectedSeats,
                    theaterName,
                )
            handler.post {
                view.navigateToResultView(ticketId)
            }
        }.start()
    }
}
