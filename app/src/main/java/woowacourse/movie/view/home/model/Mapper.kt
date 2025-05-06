package woowacourse.movie.view.home.model

import woowacourse.movie.domain.model.feed.Feed
import woowacourse.movie.domain.model.feed.Feed.Ad
import woowacourse.movie.domain.model.feed.Feed.Movie
import woowacourse.movie.view.home.model.FeedUiModel.AdUiModel
import woowacourse.movie.view.home.model.FeedUiModel.MovieUiModel
import woowacourse.movie.view.util.StringFormatter

fun Feed.toUiModel(): FeedUiModel {
    return when (this) {
        is Movie -> this.toUiModel()
        is Ad -> this.toUiModel()
    }
}

private fun Movie.toUiModel(): MovieUiModel {
    return MovieUiModel(
        id = id,
        title = title,
        imgName = posterResource,
        startDate = StringFormatter.dotDateFormat(screeningDates.startDate),
        endDate = StringFormatter.dotDateFormat(screeningDates.endDate),
        runningTime = runningTime,
    )
}

private fun Ad.toUiModel(): AdUiModel {
    return AdUiModel(
        imgResource = imgResource,
    )
}
