package woowacourse.movie.ui

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image

sealed interface ScreenAd {
    data class ScreenPreviewUi(
        val id: Int,
        val moviePreviewUI: MoviePreviewUI,
        val dateRange: DateRange,
    ) : ScreenAd

    data class Advertisement(
        val id: Int = 0,
        val advertisement: Image.DrawableImage,
    ) : ScreenAd
}

fun ScreenData.toPreviewUI(image: Image<Any>) =
    ScreenAd.ScreenPreviewUi(
        id = id,
        moviePreviewUI = movie.toPreviewUI(image),
        dateRange = dateRange,
    )
