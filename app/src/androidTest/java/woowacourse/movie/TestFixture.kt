package woowacourse.movie

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import woowacourse.movie.data.entity.MovieEntity
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.entity.ReservationInfoEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TheaterEntity
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.MovieDate
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.theater.ScreeningInfo
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.theater.TheaterMovieSchedule
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
        MOVIE,
        MovieDate(
            LocalDate.of(2025, 4, 1),
            LocalDate.of(2025, 5, 30),
        ),
        MovieTime(LocalTime.of(10, 0)),
        TicketCount(2),
        Theater(1, "JAY 극장"),
    )

val MOVIE_TICKET_B1_C3 =
    MovieTicketEntity(
        reservationInfoEntity =
            ReservationInfoEntity(
                id = 1,
                movieId = 17,
                movieDateYear = 2025,
                movieDateMonth = 5,
                movieDateDay = 10,
                movieTimeHour = 19,
                movieTimeMinute = 0,
                ticketCount = 2,
                theaterId = 5,
                price = 20000,
            ),
        seats =
            listOf(
                SeatEntity(id = 1, reservationId = 1, row = 1, column = 0),
                SeatEntity(id = 2, reservationId = 1, row = 2, column = 2),
            ),
        movie =
            MovieEntity(
                id = 17,
                title = "이상한 나라의 앨리스",
            ),
        theater =
            TheaterEntity(
                id = 5,
                name = "뭉GV",
            ),
    )

val THEATER_MOVIE_SCHEDULE: TheaterMovieSchedule =
    TheaterMovieSchedule(
        theater = Theater(1, "제이 영화관"),
        movie = MOVIE,
        screeningInfo =
            ScreeningInfo(
                listOf(MovieTime(LocalTime.of(10, 0))),
            ),
    )

val fakeContext: Context = ApplicationProvider.getApplicationContext()
