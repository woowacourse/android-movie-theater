package woowacourse.movie.mapper

import woowacourse.movie.model.Theater
import woowacourse.movie.movie.TheaterUiModel

fun Theater.toUiModel(): TheaterUiModel {
    return TheaterUiModel(
        place = place,
        schedules = schedules.map { it.toUiModel() },
    )
}

fun TheaterUiModel.toDomain(): Theater {
    return Theater(
        place = place,
        schedules = schedules.map { it.toDomain() },
    )
}
