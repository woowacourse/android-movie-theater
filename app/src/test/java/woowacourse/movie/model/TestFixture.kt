package woowacourse.movie.model

import woowacourse.movie.model.data.Theaters
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.Movies
import woowacourse.movie.model.movie.Seat
import woowacourse.movie.model.movie.Theater
import java.time.LocalDate

val movieContent =
    MovieContent(
        "movie_poster",
        "해리 포터와 마법사의 돌",
        LocalDate.of(2024, 3, 1),
        LocalDate.of(2024, 3, 28),
        152,
        "해리",
    )

val theater =
    Theater("선릉", Movies(listOf(movieContent)))

fun makeTheater(name: String): Theater {
    return Theater(name, Movies(listOf(movieContent)))
}

val A1_SEAT = Seat(0, 0)
val A2_SEAT = Seat(0, 1)
val B1_SEAT = Seat(1, 0)
val B2_SEAT = Seat(1, 1)
