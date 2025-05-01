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
        MovieTime(),
        TicketCount(2),
        theater = Theater("JAY 극장"),
    )

val SEAT_2_3 = Seat(2, 3)
val SEAT_2_2 = Seat(2, 2)

val MOVIE_TICKET_B1_C3: MovieTicket =
    MovieTicket(
        "라라랜드",
        LocalDate.of(2025, 4, 1),
        MovieTime(),
        listOf(Seat(1, 0), Seat(2, 2)),
        theater = Theater("JAY 극장"),
    )

val MOVIE_SCREENING_INFO_BY_THEATER =
    TheaterMovieSchedule(
        movie =
            Movie(
                id = 1L,
                title = "해리포터와 마법사의 돌",
                poster = R.drawable.harry_potter_rock,
                startDate = LocalDate.of(2025, 5, 1),
                endDate = LocalDate.of(2025, 6, 1),
                runningTime = 125,
            ),
        screeningInfo =
            ScreeningInfo(
                theater = Theater(name = "CGV 강남"),
                screeningTimes =
                    listOf(
                        LocalTime.of(14, 0),
                        LocalTime.of(17, 30),
                        LocalTime.of(20, 0),
                    ),
            ),
    )
