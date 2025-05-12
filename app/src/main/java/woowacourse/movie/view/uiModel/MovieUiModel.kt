package woowacourse.movie.view.uiModel

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.core.bindingadapter.ImageSource
import woowacourse.movie.view.core.util.StringFormatter

data class MovieUiModel(
    val title: String,
    val posterResource: ImageSource,
    val screeningPeriod: String,
    val runningTime: String,
)

fun Movie.toUiModel(
    datePeriodFormatter: String,
    runningTimeFormatter: String,
): MovieUiModel {
    val startDate = StringFormatter.dotDateFormat(screeningStartDate)
    val endDate = StringFormatter.dotDateFormat(screeningEndDate)

    val screeningPeriod = datePeriodFormatter.format(startDate, endDate)
    val formattedRunningTimme = runningTimeFormatter.format(runningTime)

    return MovieUiModel(
        title = title,
        posterResource = ImageSource.Resource(posterResource),
        screeningPeriod = screeningPeriod,
        runningTime = formattedRunningTimme,
    )
}
