package woowacourse.movie.model

import woowacourse.movie.domain.MovieContent
import woowacourse.movie.domain.Seat
import woowacourse.movie.domain.Theater
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val movieContent =
    MovieContent(
        imageId = "thumbnail_movie1",
        title = "해리 포터와 마법사의 돌",
        openingMovieDate = LocalDate.of(2024, 3, 1),
        endingMoviesDate = LocalDate.of(2024, 3, 28),
        runningTime = 152,
        synopsis = "해리",
        theaterIds = listOf(0L, 1L, 2L),
    )

val theater =
    Theater(
        name = "선릉",
        screeningTimes =
            listOf(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
            ),
    )

fun makeTheater(name: String): Theater {
    return Theater(
        name = name,
        screeningTimes =
            listOf(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
            ),
    )
}

val RESERVATION_DETAIL = ReservationDetail(
    title = "",
    theater = "강남",
    screeningDateTime = LocalDateTime.of(2024, 3, 28, 10, 0),
    count = 1
)

val A1_SEAT = Seat(0, 0)
val A2_SEAT = Seat(0, 1)
val B1_SEAT = Seat(1, 0)
val B2_SEAT = Seat(1, 1)
