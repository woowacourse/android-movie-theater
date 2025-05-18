package woowacourse.movie.presenter

import woowacourse.movie.R
import woowacourse.movie.data.entity.MovieEntity
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.entity.ReservationInfoEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TheaterEntity
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
        MovieTime(LocalTime.of(13, 15)),
        TicketCount(2),
        theater = Theater(1, "JAY 극장"),
    )

val SEAT_2_3 = Seat(2, 3)
val SEAT_2_2 = Seat(2, 2)

val THEATER_MOVIE_SCHEDULE_CGV =
    TheaterMovieSchedule(
        theater = Theater(1, "CGV 강남"),
        movie =
            Movie(
                id = 1L,
                title = "라라랜드",
                poster = R.drawable.lalaland,
                startDate = LocalDate.of(2025, 4, 1),
                endDate = LocalDate.of(2025, 5, 30),
                runningTime = 125,
            ),
        screeningInfo =
            ScreeningInfo(
                screeningTimes =
                    listOf(
                        MovieTime(LocalTime.of(14, 0)),
                        MovieTime(LocalTime.of(17, 30)),
                        MovieTime(LocalTime.of(20, 0)),
                    ),
            ),
    )

val THEATER_MOVIE_SCHEDULE_LOTTE =
    TheaterMovieSchedule(
        theater = Theater(2, name = "롯데시네마 강남"),
        movie =
            Movie(
                id = 1L,
                title = "라라랜드",
                poster = R.drawable.lalaland,
                startDate = LocalDate.of(2025, 4, 1),
                endDate = LocalDate.of(2025, 5, 30),
                runningTime = 125,
            ),
        screeningInfo =
            ScreeningInfo(
                screeningTimes =
                    listOf(
                        MovieTime(LocalTime.of(14, 0)),
                        MovieTime(LocalTime.of(17, 30)),
                        MovieTime(LocalTime.of(20, 0)),
                    ),
            ),
    )

val THEATER_MOVIE_SCHEDULES =
    TheaterMovieSchedules(
        setOf(THEATER_MOVIE_SCHEDULE_CGV, THEATER_MOVIE_SCHEDULE_LOTTE),
    )

val THEATER_MOVIE_SCHEDULE_MOVIE_ID_1L =
    TheaterMovieSchedules(setOf(THEATER_MOVIE_SCHEDULE_CGV, THEATER_MOVIE_SCHEDULE_LOTTE))

val RESERVATION_INFO_ENTITY =
    ReservationInfoEntity(
        id = 1L,
        movieId = 101L,
        movieDateYear = 2025,
        movieDateMonth = 5,
        movieDateDay = 12,
        movieTimeHour = 13,
        movieTimeMinute = 15,
        ticketCount = 2,
        theaterId = 201L,
        price = 20000,
    )

val SEAT_ENTITY_1_1 =
    SeatEntity(
        id = 1L,
        reservationId = 1L,
        row = 1,
        column = 1,
    )

val SEAT_ENTITY_2_2 =
    SeatEntity(
        id = 2L,
        reservationId = 1L,
        row = 2,
        column = 2,
    )

val MOVIE_ENTITY =
    MovieEntity(
        id = 1L,
        title = "라라랜드",
        poster = 123,
        movieStartDateYear = 2025,
        movieStartDateMonth = 5,
        movieStartDateDay = 12,
        movieEndDateYear = 2025,
        movieEndDateMonth = 12,
        movieEndDateDay = 12,
        runningTime = 120,
    )

val THEATER_ENTITY =
    TheaterEntity(
        id = 201L,
        name = "강남 CGV",
    )

val MOVIE_TICKET_ENTITY =
    MovieTicketEntity(
        reservationInfoEntity = RESERVATION_INFO_ENTITY,
        seats = listOf(SEAT_ENTITY_1_1, SEAT_ENTITY_2_2),
        movie = MOVIE_ENTITY,
        theater = THEATER_ENTITY,
    )

val MOVIE_TICKET_B1_C3 =
    MovieTicket(
        id = 1L,
        movie = MOVIE,
        movieDate = LocalDate.of(2025, 5, 10),
        movieTime = MovieTime(LocalTime.of(19, 0)),
        seats = listOf(Seat(1, 0), Seat(2, 2)),
        theater = Theater(1, "JAY 극장"),
    )
