package woowacourse.movie.view.movies.adapter.model

import woowacourse.movie.domain.model.Advertisement
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.core.bindingadapter.ImageSource
import woowacourse.movie.view.core.util.StringFormatter
import woowacourse.movie.view.movies.adapter.MovieAdapter.ViewType
import woowacourse.movie.view.movies.adapter.model.MovieRvItem.AdItem

sealed class MovieRvItem(val viewType: ViewType) {
    data class MovieItem(
        val id: Int,
        val title: String,
        val imgName: ImageSource,
        val releaseStartDate: String,
        val releaseEndDate: String,
        val runningTime: Int,
    ) : MovieRvItem(ViewType.VIEW_TYPE_MOVIE)

    data class AdItem(
        val imgResource: ImageSource,
    ) : MovieRvItem(ViewType.VIEW_TYPE_ADVERTISEMENT)
}

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
