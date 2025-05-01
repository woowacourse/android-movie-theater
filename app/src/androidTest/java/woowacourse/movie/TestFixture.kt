package woowacourse.movie

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.MovieDate
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.theater.ScreeningInfo
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.theater.TheaterMovieSchedule
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.model.ticket.TicketCount
import java.time.LocalDate
import java.time.LocalTime

val MOVIE: Movie =
    Movie(
        1,
        "라라랜드",
        R.drawable.lalaland,
        LocalDate.of(2025, 4, 1),
        LocalDate.of(2025, 5, 30),
        120,
    )

val MOVIE_TO_RESERVE: MovieToReserve =
    MovieToReserve(
        1,
        "라라랜드",
        MovieDate(
            LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 5, 30),
        ),
        MovieTime(LocalTime.of(10, 0)),
        TicketCount(2),
        Theater("JAY 극장"),
    )

val MOVIE_TICKET_B1_C3: MovieTicket =
    MovieTicket(
        "라라랜드",
        LocalDate.of(2025, 4, 1),
        MovieTime(LocalTime.of(10, 0)),
        listOf(Seat(1, 0), Seat(2, 2)),
        Theater("JAY 극장"),
    )

val THEATER_MOVIE_SCHEDULE: TheaterMovieSchedule =
    TheaterMovieSchedule(
        theater = Theater("제이 영화관"),
        movie = MOVIE,
        screeningInfo =
            ScreeningInfo(
                listOf(MovieTime(LocalTime.of(10, 0))),
            ),
    )

val fakeContext: Context = ApplicationProvider.getApplicationContext()
