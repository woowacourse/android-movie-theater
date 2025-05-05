package woowacourse.movie.view.home.model

import woowacourse.movie.domain.model.feed.Feed
import woowacourse.movie.domain.model.feed.Feed.Advertisement
import woowacourse.movie.domain.model.feed.Feed.Movie
import woowacourse.movie.view.home.model.FeedUiModel.AdvertisementUiModel
import woowacourse.movie.view.home.model.FeedUiModel.MovieUiModel
import woowacourse.movie.view.util.StringFormatter

fun Feed.toUiModel(): FeedUiModel {
    return when (this) {
        is Movie -> this.toUiModel()
        is Advertisement -> this.toUiModel()
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

private fun Advertisement.toUiModel(): AdvertisementUiModel {
    return AdvertisementUiModel(
        imgResource = imgResource,
    )
}
