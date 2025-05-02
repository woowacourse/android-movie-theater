package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.ad.Advertisement
import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.view.StringFormatter
import woowacourse.movie.view.home.movies.model.Item

fun Movie.toItem(): Item.MovieItem {
    return Item.MovieItem(
        id = id,
        title = title,
        imgName = posterResource,
        releaseStartDate = StringFormatter.dotDateFormat(releaseDate.startDate),
        releaseEndDate = StringFormatter.dotDateFormat(releaseDate.endDate),
        runningTime = runningTime,
    )
}

fun Advertisement.toItem(): Item.AdvertiseItem {
    return Item.AdvertiseItem(
        imgResource = imgResource,
    )
}
