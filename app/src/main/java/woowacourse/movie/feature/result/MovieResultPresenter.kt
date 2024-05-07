package woowacourse.movie.feature.result

import woowacourse.movie.data.MovieRepository
import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.unFormatSeatColumn
import woowacourse.movie.util.unFormatSeatRow
import java.time.LocalDate
import java.time.LocalTime

class MovieResultPresenter(private val view: MovieResultContract.View) :
    MovieResultContract.Presenter {
    override fun loadTicket(
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

        // TODO: DB에 Ticket 저장
        view.displayTicket(
            Ticket(
                0L,
                movie.id,
                LocalDate.parse(screeningDate),
                LocalTime.parse(screeningTime),
                movieSelectedSeats,
                theaterName,
            ),
        )
    }
}
