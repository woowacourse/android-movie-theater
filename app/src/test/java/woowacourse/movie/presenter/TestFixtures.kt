package woowacourse.movie.presenter

import woowacourse.movie.R
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.MovieDate
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.theater.ScreeningInfo
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.theater.TheaterMovieSchedule
import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.model.ticket.TicketCount
import java.time.LocalDate
import java.time.LocalTime

val MOVIE: Movie = Movie(
    1,
    "라라랜드",
    R.drawable.lalaland,
    LocalDate.of(2025, 4, 1),
    LocalDate.of(2025, 5, 30),
    120,
)

val MOVIE_TO_RESERVE: MovieToReserve = MovieToReserve(
    1,
    "라라랜드",
    MovieDate(
        LocalDate.of(2025, 4, 1),
        LocalDate.of(2025, 5, 30),
    ),
    MovieTime(LocalTime.of(13, 15)),
    TicketCount(2),
    theater = Theater("JAY 극장"),
)

val SEAT_2_3 = Seat(2, 3)
val SEAT_2_2 = Seat(2, 2)

val MOVIE_TICKET_B1_C3: MovieTicket = MovieTicket(
    "라라랜드",
    LocalDate.of(2025, 4, 1),
    MovieTime(LocalTime.of(16, 45)),
    listOf(Seat(1, 0), Seat(2, 2)),
    theater = Theater("JAY 극장"),
)

val THEATER_MOVIE_SCHEDULE_CGV = TheaterMovieSchedule(
    theater = Theater(name = "CGV 강남"),
    movie = Movie(
        id = 1L,
        title = "라라랜드",
        poster = R.drawable.lalaland,
        startDate = LocalDate.of(2025, 4, 1),
        endDate = LocalDate.of(2025, 5, 30),
        runningTime = 125,
    ),
    screeningInfo = ScreeningInfo(
        screeningTimes = listOf(
            MovieTime(LocalTime.of(14, 0)),
            MovieTime(LocalTime.of(17, 30)),
            MovieTime(LocalTime.of(20, 0)),
        ),
    ),
)

val THEATER_MOVIE_SCHEDULE_LOTTE = TheaterMovieSchedule(
    theater = Theater(name = "롯데시네마 강남"),
    movie = Movie(
        id = 1L,
        title = "라라랜드",
        poster = R.drawable.lalaland,
        startDate = LocalDate.of(2025, 4, 1),
        endDate = LocalDate.of(2025, 5, 30),
        runningTime = 125,
    ),
    screeningInfo = ScreeningInfo(
        screeningTimes = listOf(
            MovieTime(LocalTime.of(14, 0)),
            MovieTime(LocalTime.of(17, 30)),
            MovieTime(LocalTime.of(20, 0)),
        ),
    ),
)

val THEATER_MOVIE_SCHEDULES = TheaterMovieSchedules(
    listOf(THEATER_MOVIE_SCHEDULE_CGV, THEATER_MOVIE_SCHEDULE_LOTTE)
)

val THEATER_MOVIE_SCHEDULE_MOVIE_ID_1L =
    listOf(THEATER_MOVIE_SCHEDULE_CGV, THEATER_MOVIE_SCHEDULE_LOTTE)
