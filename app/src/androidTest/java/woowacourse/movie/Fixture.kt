package woowacourse.movie

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.feature.mapper.toUi

val MOVIE_UI_MODEL_01 =
    Movie(
        title = "해리 포터와 마법사의 돌",
        startDate = MovieDate(2025, 4, 1),
        endDate = MovieDate(2025, 4, 25),
        runningTime = 152,
        availableTheaters =
            listOf(
                Theater("선릉", listOf(MovieTime(9, 0), MovieTime(12, 0), MovieTime(15, 0))),
                Theater("잠실", listOf(MovieTime(10, 0), MovieTime(13, 0))),
                Theater("강남", listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0), MovieTime(20, 0))),
            ),
    ).toUi()
