package woowacourse.movie.movielist.theaters

import woowacourse.movie.model.MovieTheater

fun MovieTheater.toTheaterUiModel(totalCount: Int): TheaterUiModel =
    TheaterUiModel(
        id,
        name,
        totalCount.toString(),
    )
