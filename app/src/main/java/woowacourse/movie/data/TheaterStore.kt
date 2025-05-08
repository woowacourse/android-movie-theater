package woowacourse.movie.data

import woowacourse.movie.domain.model.feed.Feed.Movie
import woowacourse.movie.domain.model.theater.Screening
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterStore {
    private val movies = MovieStore().movies

    fun createTheaters(): Theaters {
        val theaters =
            listOf(
                Theater(
                    "선릉 극장",
                    generateTheaterScreenings(
                        listOf(0, 1, 2, 3, 5),
                        listOf(
                            LocalTime.of(13, 20),
                            LocalTime.of(15, 35),
                            LocalTime.of(16, 0),
                            LocalTime.of(18, 5),
                            LocalTime.of(19, 55),
                        ),
                    ),
                ),
                Theater(
                    "잠실 극장",
                    generateTheaterScreenings(
                        listOf(1, 2, 4, 6, 7),
                        listOf(
                            LocalTime.of(10, 45),
                            LocalTime.of(13, 20),
                            LocalTime.of(15, 35),
                            LocalTime.of(16, 0),
                        ),
                    ),
                ),
                Theater(
                    "강남 극장",
                    generateTheaterScreenings(
                        listOf(1, 2, 5, 6, 7),
                        listOf(
                            LocalTime.of(10, 45),
                            LocalTime.of(11, 35),
                            LocalTime.of(13, 20),
                            LocalTime.of(16, 0),
                            LocalTime.of(18, 5),
                            LocalTime.of(19, 55),
                        ),
                    ),
                ),
            )

        return Theaters(theaters)
    }

    private fun generateTheaterScreenings(
        movieIds: List<Int>,
        screeningTimes: List<LocalTime>,
    ): List<Screening> =
        movieIds.mapNotNull { movieId ->
            movies.find { movie -> movie.id == movieId }
        }.flatMap { movie -> generateMovieScreenings(movie, screeningTimes) }

    private fun generateMovieScreenings(
        movie: Movie,
        screeningTimes: List<LocalTime>,
    ): List<Screening> {
        val screeningDates = generateDateRange(movie.startDate, movie.endDate)
        return screeningDates.flatMap { date ->
            screeningTimes.map { time -> Screening(movie.id, LocalDateTime.of(date, time)) }
        }
    }

    private fun generateDateRange(
        startDate: LocalDate,
        endDate: LocalDate,
    ) = generateSequence(startDate) { date ->
        if (date.isBefore(endDate)) date.plusDays(1) else null
    }.toList()
}
