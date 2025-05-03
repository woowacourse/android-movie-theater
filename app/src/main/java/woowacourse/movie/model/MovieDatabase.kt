package woowacourse.movie.model

import woowacourse.movie.R
import woowacourse.movie.view.item.Movie
import java.time.LocalDate

object MovieDatabase {
    val screenings =
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

    val movies =
        mapOf(
            "라라랜드" to
                Movie(
                    "라라랜드",
                    R.drawable.lalaland,
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 4, 30),
                    123,
                ),
            "승부" to
                Movie(
                    "승부",
                    R.drawable.match,
                    LocalDate.of(2025, 4, 11),
                    LocalDate.of(2025, 4, 30),
                    114,
                ),
            "야당" to
                Movie(
                    "야당",
                    R.drawable.yadang,
                    LocalDate.of(2025, 4, 21),
                    LocalDate.of(2025, 4, 30),
                    109,
                ),
            "월플라워" to
                Movie(
                    "월플라워",
                    R.drawable.wall_flower,
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 5, 30),
                    129,
                ),
            "줄무늬 파자마를 입은 소년" to
                Movie(
                    "줄무늬 파자마를 입은 소년",
                    R.drawable.pajama,
                    LocalDate.of(2025, 4, 11),
                    LocalDate.of(2025, 5, 30),
                    123,
                ),
            "A.I." to
                Movie(
                    "A.I.",
                    R.drawable.movie_ai,
                    LocalDate.of(2025, 4, 21),
                    LocalDate.of(2025, 5, 30),
                    149,
                ),
            "이미테이션 게임" to
                Movie(
                    "이미테이션 게임",
                    R.drawable.imitation_game,
                    LocalDate.of(2025, 4, 21),
                    LocalDate.of(2025, 5, 30),
                    130,
                ),
        )
}
