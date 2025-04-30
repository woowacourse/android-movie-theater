package woowacourse.movie.data

import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.domain.model.theater.Screening
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterStore {
    private val timeSlots =
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
                Theater("선릉 극장", generateScreeningsForTheater(movies, listOf(0, 1, 2, 3, 4, 5, 6))),
                Theater("잠실 극장", generateScreeningsForTheater(movies, listOf(0, 1, 2, 3, 4, 5, 6))),
                Theater("강남 극장", generateScreeningsForTheater(movies, listOf(0, 1, 2, 4, 5, 6, 7))),
            )

        return Theaters(theaters)
    }

    private fun generateScreeningsForTheater(
        movies: List<Movie>,
        theaterMovieIds: List<Int>,
    ): List<Screening> {
        return theaterMovieIds
            .mapNotNull { id -> movies.find { it.id == id } }
            .flatMap { movie -> generateScreeningsForMovie(movie) }
    }

    private fun generateScreeningsForMovie(movie: Movie): List<Screening> {
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
            if (current.isBefore(end)) current.plusDays(1) else null
        }.toList() + end
    }
}
