package woowacourse.movie.data

import woowacourse.movie.domain.Screening
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.repository.TheaterRepository

object TheaterMockRepository : TheaterRepository {

    private val theaters = listOf<Theater>(
        Theater(
            "선릉 극장",
            listOf(
                Screening(1, listOf(0, 1, 2, 5, 7)),
                Screening(2, listOf(2, 3, 4, 5)),
                Screening(3, listOf(3)),
                Screening(4, listOf(5, 6))
            )
        ),
        Theater(
            "잠실 극장",
            listOf(
                Screening(1, listOf(0, 2)),
                Screening(2, listOf(2, 3, 4, 5)),
                Screening(3, listOf(1, 3, 4)),
                Screening(4, listOf(5, 6))
            )
        ),
        Theater(
            "강남 극장",
            listOf(
                Screening(1, listOf(0, 2)),
                Screening(3, listOf(2, 3, 4, 5)),
                Screening(5, listOf(1, 3, 4)),
                Screening(6, listOf(5, 6))
            )
        ),
        Theater(
            "너무너무너무너무너무 긴 극장",
            listOf(
                Screening(1, listOf(0, 2)),
                Screening(2, listOf(2, 3, 4, 5)),
                Screening(3, listOf(1, 3, 4)),
                Screening(5, listOf(5, 6))
            )
        )
    )

    override fun findTheaterByMovieId(movieId: Int): List<Theater> {
        return theaters.filter { theater ->
            theater.screenings.find { screening ->
                screening.movieId == movieId
            } != null
        }
    }
}
