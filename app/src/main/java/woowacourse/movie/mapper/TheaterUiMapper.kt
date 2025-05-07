package woowacourse.movie.mapper

import woowacourse.movie.model.Movie
import woowacourse.movie.model.Theater
import woowacourse.movie.ui.model.TheaterUiModel

fun Theater.toUiModel(movie: Movie): TheaterUiModel {
    return TheaterUiModel(
        place = place,
        schedule = schedules.find { it.movie == movie }!!.toUiModel(),
    )
}
