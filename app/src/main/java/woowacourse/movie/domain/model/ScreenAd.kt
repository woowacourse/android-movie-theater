package woowacourse.movie.domain.model

import woowacourse.movie.ui.MoviePreviewUI
import woowacourse.movie.ui.toPreviewUI

sealed interface ScreenAd {
    data class ScreenPreviewUi(
        val id: Int,
        val moviePreviewUI: MoviePreviewUI,
        val dateRange: DateRange,
    ) : ScreenAd

    data class Advertisement(
        val id: Int = 0,
        val advertisement: DrawableImage,
    ) : ScreenAd
}

fun Screen.toPreviewUI(image: Image<Any>) =
    ScreenAd.ScreenPreviewUi(
        id = id,
        moviePreviewUI = movie.toPreviewUI(image),
        dateRange = dateRange,
    )
