package woowacourse.movie.mapper

import woowacourse.movie.model.Movie
import woowacourse.movie.model.Theater
import woowacourse.movie.ui.model.TheaterUiModel

fun Theater.toUiModel(movie: Movie): TheaterUiModel {
    return TheaterUiModel(
        place = place,
        schedule = screeningInfos.find { it.movie == movie }?.toUiModel() ?: throw IllegalArgumentException(ERROR_NOT_FOUND_MOVIE),
    )
}

private const val ERROR_NOT_FOUND_MOVIE = "%s을 상영하는 영화관을 찾을 수 없습니다"
