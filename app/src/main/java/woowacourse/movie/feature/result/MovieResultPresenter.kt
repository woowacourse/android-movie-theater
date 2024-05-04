package woowacourse.movie.feature.result

import woowacourse.movie.data.MovieRepository.getMovieById
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.util.unFormatSeatColumn
import woowacourse.movie.util.unFormatSeatRow
import java.time.LocalDate
import java.time.LocalTime

class MovieResultPresenter(private val movieResultContractView: MovieResultContract.View) :
    MovieResultContract.Presenter {
    override fun loadMovieTicket(
        id: Long,
        date: String,
        time: String,
        count: Int,
        seats: String,
        theaterName: String,
    ) {
        val movieData = getMovieById(id)

        val movieSelectedSeats = MovieSelectedSeats(count)
        seats.split(", ").forEach { seat ->
            movieSelectedSeats.selectSeat(
                MovieSeat(
                    seat.unFormatSeatRow(),
                    seat.unFormatSeatColumn(),
                ),
            )
        }

        movieResultContractView.displayMovieTicket(
            movieData?.let { movie ->
                MovieTicket(
                    movie.title,
                    LocalDate.parse(date),
                    LocalTime.parse(time),
                    count,
                    movieSelectedSeats,
                    theaterName,
                )
            },
        )
    }
}
