package woowacourse.movie.data

import woowacourse.movie.domain.model.theater.Screening
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDateTime

class TheaterStore {
    fun createTheaters(): Theaters {
        val movies = MovieStore().getAll()

        val sunnleng =
            listOf(
                Screening(1, LocalDateTime.of(2025, 4, 29, 9, 30)),
                Screening(1, LocalDateTime.of(2025, 4, 29, 13, 0)),
                Screening(1, LocalDateTime.of(2025, 4, 29, 16, 30)),
                Screening(1, LocalDateTime.of(2025, 4, 29, 20, 0)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 10, 0)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 14, 0)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 18, 0)),
                Screening(3, LocalDateTime.of(2025, 4, 29, 11, 0)),
                Screening(3, LocalDateTime.of(2025, 4, 29, 14, 0)),
                Screening(3, LocalDateTime.of(2025, 4, 29, 17, 0)),
                Screening(5, LocalDateTime.of(2025, 4, 29, 12, 30)),
                Screening(5, LocalDateTime.of(2025, 4, 29, 15, 30)),
            )

        val jamsil =
            listOf(
                Screening(1, LocalDateTime.of(2025, 4, 29, 10, 0)),
                Screening(1, LocalDateTime.of(2025, 4, 29, 14, 0)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 9, 0)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 11, 30)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 14, 0)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 16, 30)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 19, 0)),
                Screening(2, LocalDateTime.of(2025, 4, 29, 21, 30)),
                Screening(4, LocalDateTime.of(2025, 4, 29, 10, 30)),
                Screening(4, LocalDateTime.of(2025, 4, 29, 14, 0)),
                Screening(4, LocalDateTime.of(2025, 4, 29, 18, 0)),
                Screening(6, LocalDateTime.of(2025, 4, 29, 11, 0)),
                Screening(6, LocalDateTime.of(2025, 4, 29, 15, 0)),
                Screening(6, LocalDateTime.of(2025, 4, 29, 19, 0)),
            )

        val gangnam =
            listOf(
                // 모든 영화를 상영하지만 상영 시간은 다르게 (러닝 타임 반영)
                movies.flatMap { movie ->
                    listOf(
                        Screening(
                            movie.id,
                            LocalDateTime.of(2025, 4, 29, 8, 0).plusMinutes(movie.runningTime.toLong()),
                        ),
                        Screening(
                            movie.id,
                            LocalDateTime.of(2025, 4, 29, 11, 0).plusMinutes(movie.runningTime.toLong()),
                        ),
                        Screening(
                            movie.id,
                            LocalDateTime.of(2025, 4, 29, 14, 0).plusMinutes(movie.runningTime.toLong()),
                        ),
                        Screening(
                            movie.id,
                            LocalDateTime.of(2025, 4, 29, 17, 0).plusMinutes(movie.runningTime.toLong()),
                        ),
                        Screening(
                            movie.id,
                            LocalDateTime.of(2025, 4, 29, 20, 0).plusMinutes(movie.runningTime.toLong()),
                        ),
                    )
                },
            ).flatten()

        return Theaters(
            listOf(
                Theater("선릉 극장", sunnleng),
                Theater("잠실 극장", jamsil),
                Theater("강남 극장", gangnam),
            ),
        )
    }
}
