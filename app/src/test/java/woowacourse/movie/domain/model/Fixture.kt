package woowacourse.movie.domain.model

import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieDatabaseSchema
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.model.ReservationInfo
import woowacourse.movie.model.Seats
import woowacourse.movie.model.TheaterUIModel
import java.time.LocalDate

val dummyMovie =
    Movie(
        "라라랜드",
        R.drawable.lalaland,
        LocalDate.of(2025, 4, 1),
        LocalDate.of(2025, 4, 30),
        120,
    )

val dummyTicket =
    MovieTicket(
        "라라랜드",
        LocalDate.of(2025, 4, 1),
        "14:00",
        3,
        "선릉",
    )

val dummyReservationInfo =
    ReservationInfo(
        "라라랜드",
        LocalDate.of(2025, 4, 1),
        "14:00",
        Seats.create(),
        20000,
        "선릉",
    )

val dummyUIModel =
    TheaterUIModel("선릉", dummyMovie, 1)

val dummyMovieDatabase = DummyMovieDatabase()

class DummyMovieDatabase : MovieDatabaseSchema {
    override val screenings: Map<String, Map<String, List<Int>>> =
        mapOf(
            "선릉" to
                mapOf(
                    "라라랜드" to listOf(10, 13, 16),
                ),
        )

    override val movies: Map<String, Movie> =
        mapOf(
            "라라랜드" to
                dummyMovie,
        )
}
