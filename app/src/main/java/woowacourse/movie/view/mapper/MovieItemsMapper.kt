package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.ad.Advertisement
import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.view.StringFormatter
import woowacourse.movie.view.bindingadapter.ImageSource
import woowacourse.movie.view.home.movies.model.MovieRvItem

fun Movie.toItem(): MovieRvItem.MovieItem {
    return MovieRvItem.MovieItem(
        id = id,
        title = title,
        imgName = ImageSource.Resource(posterResource),
        releaseStartDate = StringFormatter.dotDateFormat(releaseDate.startDate),
        releaseEndDate = StringFormatter.dotDateFormat(releaseDate.endDate),
        runningTime = runningTime,
    )
}

fun Advertisement.toItem(): MovieRvItem.AdItem {
    return MovieRvItem.AdItem(
        imgResource = ImageSource.Resource(imgResource),
    )
}
