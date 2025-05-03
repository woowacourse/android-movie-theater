package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.view.StringFormatter
import woowacourse.movie.view.bindingadapter.ImageSource
import woowacourse.movie.view.uiModel.MovieUiModel

fun Movie.toUiModel(): MovieUiModel {
    return MovieUiModel(
        title = title,
        posterResource = ImageSource.Resource(posterResource),
        screeningStartDate = StringFormatter.dotDateFormat(screeningStartDate),
        screeningEndDate = StringFormatter.dotDateFormat(screeningEndDate),
        runningTime = runningTime.toString(),
    )
}
