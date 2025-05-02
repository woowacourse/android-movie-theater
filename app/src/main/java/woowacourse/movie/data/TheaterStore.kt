package woowacourse.movie.data

import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.domain.model.theater.Screening
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterStore {
    private val defaultTimeSlots =
        listOf(
            LocalTime.of(9, 0),
            LocalTime.of(13, 0),
            LocalTime.of(17, 0),
            LocalTime.of(21, 0),
        )

    fun createTheaters(): Theaters {
        val movies = MovieStore().getAll()

        val theaters =
            listOf(
                Theater(
                    "선릉 극장",
                    generateScreeningsForTheater(
                        movies,
                        listOf(0, 1, 2, 3, 5),
                        listOf(
                            LocalTime.of(10, 0),
                            LocalTime.of(14, 0),
                            LocalTime.of(18, 0),
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
                            LocalTime.of(19, 30),
                            LocalTime.of(22, 0),
                        ),
                    ),
                ),
                Theater(
                    "강남 극장",
                    generateScreeningsForTheater(
                        movies,
                        listOf(1, 2, 5, 6, 7),
                        defaultTimeSlots,
                    ),
                ),
            )

        return Theaters(theaters)
    }

    private fun generateScreeningsForTheater(
        movies: List<Movie>,
        theaterMovieIds: List<Int>,
        theaterTimeSlots: List<LocalTime>,
    ): List<Screening> {
        return theaterMovieIds
            .mapNotNull { id -> movies.find { it.id == id } }
            .flatMap { movie -> generateScreeningsForMovie(movie, theaterTimeSlots) }
    }

    private fun generateScreeningsForMovie(
        movie: Movie,
        timeSlots: List<LocalTime>,
    ): List<Screening> {
        val dateRange = generateDateRange(movie.releaseDate.startDate, movie.releaseDate.endDate)

        return dateRange.flatMap { date ->
            timeSlots.map { time ->
                Screening(
                    movieId = movie.id,
                    screenTime = LocalDateTime.of(date, time),
                )
            }
        }
    }

    private fun generateDateRange(
        start: LocalDate,
        end: LocalDate,
    ): List<LocalDate> {
        return generateSequence(start) { current ->
            if (current < end) current.plusDays(1) else null
        }.toList()
    }
}
