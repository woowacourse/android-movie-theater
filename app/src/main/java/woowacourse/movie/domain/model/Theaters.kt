package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

class Theaters(
    val theaters: List<Theater>,
) : Serializable {
    fun availableTheaters(
        movie: Movie,
        date: LocalDate = LocalDate.now(),
        time: LocalTime = LocalTime.now(),
    ): Theaters {
        val filteredTheaters: List<Theater> =
            theaters.map { theater ->
                theater.theaterByMovie(movie, date, time)
            }
        return Theaters(filteredTheaters)
    }
}
