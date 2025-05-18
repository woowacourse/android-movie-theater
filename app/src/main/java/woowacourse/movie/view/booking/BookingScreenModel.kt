package woowacourse.movie.view.booking

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.core.bindingadapter.ImageSource
import woowacourse.movie.view.core.util.StringFormatter

data class BookingScreenModel(
    val title: String,
    val posterResource: ImageSource,
    val screeningPeriod: String,
    val runningTime: String,
)

fun Movie.toBookingScreen(
    datePeriodFormatter: String,
    runningTimeFormatter: String,
): BookingScreenModel {
    val startDate = StringFormatter.dotDateFormat(screeningStartDate)
    val endDate = StringFormatter.dotDateFormat(screeningEndDate)

    val screeningPeriod = datePeriodFormatter.format(startDate, endDate)
    val formattedRunningTimme = runningTimeFormatter.format(runningTime)

    return BookingScreenModel(
        title = title,
        posterResource = ImageSource.Resource(posterResource),
        screeningPeriod = screeningPeriod,
        runningTime = formattedRunningTimme,
    )
}
