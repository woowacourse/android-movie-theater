package woowacourse.movie.data.cinema

import woowacourse.movie.domain.model.tools.cinema.Cinema
import woowacourse.movie.domain.model.tools.cinema.MovieTimes

object MockCinemaData : CinemaData {
    override fun getCinemas() = listOf(
        Cinema.of(
            "선릉",
            1L to MovieTimes.of(9, 15, 2),
            2L to MovieTimes.of(12, 22, 1),
            3L to MovieTimes.of(15, 23, 2),
        ),
        Cinema.of(
            "잠실",
            2L to MovieTimes.of(14, 18, 1),
            3L to MovieTimes.of(15, 23, 2),
        ),
        Cinema.of(
            "강남",
            1L to MovieTimes.of(9, 15, 2),
            2L to MovieTimes.of(12, 14, 1),
        ),
        Cinema.of(
            "석촌",
            2L to MovieTimes.of(14, 18, 1),
            3L to MovieTimes.of(15, 23, 2),
        ),
        Cinema.of(
            "역삼",
            1L to MovieTimes.of(9, 15, 2),
            2L to MovieTimes.of(14, 18, 1),
            3L to MovieTimes.of(15, 23, 2),
        ),
        Cinema.of(
            "삼성",
            1L to MovieTimes.of(9, 15, 2),
            2L to MovieTimes.of(12, 18, 1),
            3L to MovieTimes.of(15, 23, 2),
        ),
    )
}
