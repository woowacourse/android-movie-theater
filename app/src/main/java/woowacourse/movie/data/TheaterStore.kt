package woowacourse.movie.data

import woowacourse.movie.domain.model.feed.Feed.Movie
import woowacourse.movie.domain.model.theater.Screening
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterStore {
    fun createTheaters(): Theaters {
        val movies = MovieStore().movies

        val theaters =
            listOf(
                Theater(
                    "선릉 극장",
                    generateScreeningsForTheater(
                        movies,
                        listOf(0, 1, 2, 3, 5),
                        listOf(
                            LocalTime.of(10, 10),
                            LocalTime.of(14, 40),
                            LocalTime.of(18, 20),
                        ),
                    ),
                ),
                Theater(
                    "잠실 극장",
                    generateScreeningsForTheater(
                        movies,
                        listOf(1, 2, 4, 6, 7),
                        listOf(
                            LocalTime.of(9, 30),
                            LocalTime.of(12, 0),
                            LocalTime.of(15, 20),
                            LocalTime.of(19, 40),
                            LocalTime.of(22, 50),
                        ),
                    ),
                ),
                Theater(
                    "강남 극장",
                    generateScreeningsForTheater(
                        movies,
                        listOf(1, 2, 5, 6, 7),
                        listOf(
                            LocalTime.of(9, 15),
                            LocalTime.of(13, 30),
                            LocalTime.of(17, 0),
                            LocalTime.of(21, 20),
                        ),
                    ),
                ),
            )

        return Theaters(theaters)
    }

    private fun generateScreeningsForTheater(
        movies: List<Movie>,
        movieIds: List<Int>,
        timeslots: List<LocalTime>,
    ): List<Screening> {
        return movieIds
            .mapNotNull { id -> movies.find { movie -> movie.id == id } }
            .flatMap { movie -> generateScreeningsForMovie(movie, timeslots) }
    }

    private fun generateScreeningsForMovie(
        movie: Movie,
        timeSlots: List<LocalTime>,
    ): List<Screening> {
        val dateRange = generateDateRange(movie.screeningDates.startDate, movie.screeningDates.endDate)
        return dateRange.flatMap { date ->
            timeSlots.map { time -> Screening(movie.id, LocalDateTime.of(date, time)) }
        }
    }

    private fun generateDateRange(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<LocalDate> {
        return generateSequence(startDate) { current ->
            if (current.isBefore(endDate)) current.plusDays(1) else null
        }.toList()
    }
}
