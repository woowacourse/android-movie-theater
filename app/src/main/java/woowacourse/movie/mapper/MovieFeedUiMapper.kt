package woowacourse.movie.mapper

import woowacourse.movie.R
import woowacourse.movie.model.MovieFeed
import woowacourse.movie.ui.model.MovieFeedUiModel

fun MovieFeed.toUiModel(): MovieFeedUiModel {
    return when (this) {
        is MovieFeed.AdvertisementItem -> MovieFeedUiModel.AdvertisementItem(adIdToResIdMap[id] ?: R.drawable.img_advertisement)
        is MovieFeed.MovieItem -> MovieFeedUiModel.MovieItem(this.movie.toUiModel())
    }
}

private val adIdToResIdMap =
    mapOf(
        0 to R.drawable.img_advertisement,
    )
