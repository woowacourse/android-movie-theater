package woowacourse.movie.view.home.model

import woowacourse.movie.domain.model.ad.Advertisement
import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.view.home.model.UiModel.MovieUiModel
import woowacourse.movie.view.util.StringFormatter

sealed interface UiModel {
    data class MovieUiModel(
        val id: Int,
        val title: String,
        val imgName: String,
        val startDate: String,
        val endDate: String,
        val runningTime: Int,
    ) : UiModel

    data class AdvertiseUiModel(
        val imgResource: String,
    ) : UiModel
}

fun Movie.toUiModel(): MovieUiModel {
    return MovieUiModel(
        id = id,
        title = title,
        imgName = posterResource,
        startDate = StringFormatter.dotDateFormat(screeningDates.startDate),
        endDate = StringFormatter.dotDateFormat(screeningDates.endDate),
        runningTime = runningTime,
    )
}

fun Advertisement.toUiModel(): UiModel.AdvertiseUiModel {
    return UiModel.AdvertiseUiModel(
        imgResource = imgResource,
    )
}
