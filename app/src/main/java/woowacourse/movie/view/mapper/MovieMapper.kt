package woowacourse.movie.view.mapper

import woowacourse.movie.domain.Minute
import woowacourse.movie.domain.Movie
import woowacourse.movie.view.model.MovieListModel.MovieUiModel

fun Movie.toUiModel(): MovieUiModel = MovieUiModel(
    id,
    title,
    screeningStartDate,
    screeningEndDate,
    runningTime.value,
    posterResourceId,
    summary
)

fun MovieUiModel.toDomainModel(): Movie = Movie(
    id,
    title,
    screeningStartDate,
    screeningEndDate,
    Minute(runningTime),
    posterResourceId,
    summary
)
