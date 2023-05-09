package woowacourse.movie.view.mapper

import woowacourse.movie.R
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.view.model.MovieUiModel

val posters = mapOf(
    "스즈메의 문단속" to R.drawable.suzume_poster,
    "해리 포터와 마법사의 돌" to R.drawable.harry_potter1_poster,
    "스타워즈" to R.drawable.starwars_poster,
    "어벤져스: 엔드게임" to R.drawable.avengers_endgame_poster,
)

fun Movie.toUiModel(): MovieUiModel = MovieUiModel(
    title,
    startDate,
    endDate,
    runningTime.value,
    posters[title] ?: 0,
    summary,
    schedule.schedule,
)
