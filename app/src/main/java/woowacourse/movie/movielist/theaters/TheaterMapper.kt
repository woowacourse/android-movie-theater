package woowacourse.movie.movielist.theaters

import woowacourse.movie.model.Theater

fun Theater.toTheaterUiModel(totalCount: Int): TheaterUiModel =
    TheaterUiModel(
        id,
        name,
        totalCount.toString(),
    )
