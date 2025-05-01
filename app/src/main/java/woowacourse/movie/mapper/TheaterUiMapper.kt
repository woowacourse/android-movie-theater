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

fun TheaterUiModel.toDomain(): Theater {
    return Theater(
        place = place,
        schedules = listOf(schedule.toDomain()),
    )
}
