package woowacourse.movie.view.mapper

import androidx.annotation.DrawableRes
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.view.model.MovieUiModel

fun Movie.toUiModel(@DrawableRes posterResourceId: Int): MovieUiModel = MovieUiModel(
    title,
    startDate,
    endDate,
    runningTime.value,
    posterResourceId,
    summary,
    schedule.schedule,
)
