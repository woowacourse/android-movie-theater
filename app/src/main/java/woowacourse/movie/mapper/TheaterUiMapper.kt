package woowacourse.movie.mapper

import woowacourse.movie.model.Theater
import woowacourse.movie.movie.TheaterUiModel

fun Theater.toUiModel(): TheaterUiModel {
    return TheaterUiModel(
        place = place,
        movies = movies.map { it.toUiModel() },
    )
}

fun TheaterUiModel.toDomain(): Theater {
    return Theater(
        place = place,
        movies = movies.map { it.toDomain() },
    )
}
