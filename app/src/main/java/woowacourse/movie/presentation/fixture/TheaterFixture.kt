package woowacourse.movie.presentation.fixture

import woowacourse.movie.domain.model.cinema.MovieSchedule
import woowacourse.movie.domain.model.cinema.Theater
import woowacourse.movie.domain.model.cinema.Theaters
import java.time.LocalDateTime
import kotlin.random.Random

private fun createDummyDateTimes(): List<LocalDateTime> {
    val dateTimes = mutableListOf<LocalDateTime>()
    var current = LocalDateTime.of(2025, 5, 1, 9, 0)
    repeat(10) {
        dateTimes.add(current)
        current =
            if (Random.nextBoolean()) {
                current.plusDays(2).withHour(9).withMinute(0)
            } else {
                current.plusHours(2)
            }
    }

    return dateTimes.distinct()
}

private fun createDummyMovieSchedule(
    movieId: Int,
    dateTimes: List<LocalDateTime>,
): MovieSchedule = MovieSchedule(movieId, dateTimes)

val dummySeolleungTheater =
    Theater(
        "선릉 극장",
        listOf(
            createDummyMovieSchedule(1, createDummyDateTimes()),
            createDummyMovieSchedule(2, createDummyDateTimes()),
            createDummyMovieSchedule(3, createDummyDateTimes()),
            createDummyMovieSchedule(4, createDummyDateTimes()),
        ),
    )

val dummyJamSilTheater =
    Theater(
        "잠실 극장",
        listOf(
            createDummyMovieSchedule(3, createDummyDateTimes()),
            createDummyMovieSchedule(4, createDummyDateTimes()),
            createDummyMovieSchedule(5, createDummyDateTimes()),
            createDummyMovieSchedule(6, createDummyDateTimes()),
        ),
    )

val dummyGangNamTheater =
    Theater(
        "강남 극장",
        listOf(
            createDummyMovieSchedule(5, createDummyDateTimes()),
            createDummyMovieSchedule(6, createDummyDateTimes()),
            createDummyMovieSchedule(7, createDummyDateTimes()),
            createDummyMovieSchedule(8, createDummyDateTimes()),
        ),
    )

val dummyTheaters = Theaters(listOf(dummySeolleungTheater, dummyJamSilTheater, dummyGangNamTheater))
