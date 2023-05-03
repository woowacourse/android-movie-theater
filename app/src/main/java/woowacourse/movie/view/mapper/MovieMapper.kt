package woowacourse.movie.view.mapper

import woowacourse.movie.domain.movie.Minute
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.view.model.MovieListModel.MovieUiModel

fun Movie.toUiModel(): MovieUiModel = MovieUiModel(
    title,
    getAllScreeningDates(),
    runningTime.value,
    posterResourceId,
    summary
)

fun MovieUiModel.toDomainModel(): Movie = Movie(
    title,
    screeningDates.min(),
    screeningDates.max(),
    Minute(runningTime),
    posterResourceId,
    summary
)
