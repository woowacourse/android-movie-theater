package woowacourse.movie.domain.model

import woowacourse.movie.ui.ScreenAd

sealed interface ScreenAndAd {
    data class Screen(
        val id: Int,
        val movie: Movie,
        val dateRange: DateRange,
    ) : ScreenAndAd

    data class Advertisement(
        val id: Int,
        val content: String,
        val image: Image<Any>,
    ) : ScreenAndAd
}

fun ScreenAndAd.Screen.toPreviewUI(): ScreenAd.ScreenPreviewUi =
    ScreenAd.ScreenPreviewUi(
        id = id,
        dateRange = dateRange,
        moviePreviewUI = movie.toPreviewUI(),
    )
