package woowacourse.movie.view.model

import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters
import java.time.LocalDate

object MovieFixture {
    private val dummyMovies =
        mapOf(
            "라라랜드" to
                Movie(
                    "라라랜드",
                    R.drawable.lalaland,
                    MovieDate(LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 30)),
                    123,
                ),
            "승부" to
                Movie(
                    "승부",
                    R.drawable.match,
                    MovieDate(LocalDate.of(2025, 4, 11), LocalDate.of(2025, 4, 30)),
                    114,
                ),
            "야당" to
                Movie(
                    "야당",
                    R.drawable.yadang,
                    MovieDate(LocalDate.of(2025, 4, 21), LocalDate.of(2025, 4, 30)),
                    109,
                ),
            "월플라워" to
                Movie(
                    "월플라워",
                    R.drawable.wall_flower,
                    MovieDate(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
                    129,
                ),
            "줄무늬 파자마를 입은 소년" to
                Movie(
                    "줄무늬 파자마를 입은 소년",
                    R.drawable.pajama,
                    MovieDate(LocalDate.of(2025, 4, 11), LocalDate.of(2025, 5, 30)),
                    123,
                ),
            "A.I." to
                Movie(
                    "A.I.",
                    R.drawable.movie_ai,
                    MovieDate(LocalDate.of(2025, 4, 21), LocalDate.of(2025, 5, 30)),
                    149,
                ),
            "이미테이션 게임" to
                Movie(
                    "이미테이션 게임",
                    R.drawable.imitation_game,
                    MovieDate(LocalDate.of(2025, 4, 21), LocalDate.of(2025, 5, 30)),
                    130,
                ),
        )

    private val dummyTheaterMap =
        mapOf(
            "선릉" to
                mapOf(
                    "이미테이션 게임" to listOf(10, 13, 15, 18),
                    "라라랜드" to listOf(11, 14, 17, 20),
                    "승부" to listOf(9, 12, 19),
                    "줄무늬 파자마를 입은 소년" to listOf(16, 19),
                ),
            "잠실" to
                mapOf(
                    "라라랜드" to listOf(9, 12, 15, 18),
                    "A.I." to listOf(10, 13, 16, 19),
                    "월플라워" to listOf(11, 14, 17),
                    "야당" to listOf(20),
                ),
            "강남" to
                mapOf(
                    "이미테이션 게임" to listOf(9, 12, 15),
                    "승부" to listOf(11, 14, 17),
                    "A.I." to listOf(10, 13, 16, 20),
                    "월플라워" to listOf(18, 21),
                    "줄무늬 파자마를 입은 소년" to listOf(19),
                    "야당" to listOf(10, 14),
                ),
        )

    val dummyTheaters: Theaters =
        Theaters(
            listOf("선릉", "잠실", "강남").map { theaterName ->
                val schedule = dummyTheaterMap[theaterName] ?: emptyMap()
                val screenings =
                    schedule.mapNotNull { (title, times) ->
                        val movie = dummyMovies[title] ?: error("Movie not found: $title")
                        if (times.isEmpty()) null else Screening(movie, times)
                    }
                Theater(theaterName, screenings)
            },
        )
}
