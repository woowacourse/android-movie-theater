package woowacourse.movie.feature.result

import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.util.unFormatSeatColumn
import woowacourse.movie.util.unFormatSeatRow
import java.time.LocalDate
import java.time.LocalTime

class MovieResultPresenter(private val view: MovieResultContract.View) :
    MovieResultContract.Presenter {
    override fun loadMovieTicket(
        movieId: Long,
        screeningDate: String,
        screeningTime: String,
        reservationCount: Int,
        selectedSeats: String,
        theaterName: String,
    ) {
        val movie =
            runCatching {
                MovieRepository.getMovieById(movieId)
            }.getOrElse {
                view.showToastInvalidMovieIdError(it)
                return
            }

        val movieSelectedSeats = MovieSelectedSeats(reservationCount)
        selectedSeats.split(", ").forEach { seat ->
            movieSelectedSeats.selectSeat(
                MovieSeat(
                    seat.unFormatSeatRow(),
                    seat.unFormatSeatColumn(),
                ),
            )
        }

        view.displayMovieTicket(
            MovieTicket(
                movie.title,
                LocalDate.parse(screeningDate),
                LocalTime.parse(screeningTime),
                reservationCount,
                movieSelectedSeats,
                theaterName,
            ),
        )
    }
}
