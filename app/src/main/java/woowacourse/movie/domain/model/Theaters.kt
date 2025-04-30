package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

class Theaters(
    val theaters: List<Theater>,
) : Serializable {
    fun availableTheaters(
        movie: Movie,
        date: LocalDate,
        time: LocalTime,
    ): Theaters {
        val filteredTheaters: List<Theater> =
            theaters.map { theater ->
                theater.movieSchedulesByMovie(movie, date, time)
            }
        return Theaters(filteredTheaters)
    }
}
