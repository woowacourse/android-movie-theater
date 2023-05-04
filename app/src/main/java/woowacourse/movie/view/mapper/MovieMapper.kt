package woowacourse.movie.view.mapper

import androidx.annotation.DrawableRes
import woowacourse.movie.domain.movie.Minute
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.domain.movie.ScreeningDateTimes
import woowacourse.movie.view.model.MovieUiModel

fun Movie.toUiModel(@DrawableRes posterResourceId: Int): MovieUiModel = MovieUiModel(
    title,
    screeningDateTimes.dateTimes,
    runningTime.value,
    posterResourceId,
    summary
)

fun MovieUiModel.toDomainModel(): Movie = Movie(
    title,
    ScreeningDateTimes(screeningDateTimes),
    Minute(runningTime),
    summary
)
