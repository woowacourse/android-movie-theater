package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.Advertisement
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.core.util.StringFormatter
import woowacourse.movie.view.core.bindingadapter.ImageSource
import woowacourse.movie.view.movies.model.MovieRvItem
import woowacourse.movie.view.movies.model.MovieRvItem.AdItem

fun Movie.toItem(): MovieRvItem.MovieItem {
    return MovieRvItem.MovieItem(
        id = id,
        title = title,
        imgName = ImageSource.Resource(posterResource),
        releaseStartDate = StringFormatter.dotDateFormat(screeningStartDate),
        releaseEndDate = StringFormatter.dotDateFormat(screeningEndDate),
        runningTime = runningTime,
    )
}

fun Advertisement.toItem(): AdItem {
    return AdItem(
        imgResource = ImageSource.Resource(imgResource),
    )
}
