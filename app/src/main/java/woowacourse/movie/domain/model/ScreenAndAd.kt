package woowacourse.movie.domain.model

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.ui.ScreenAd
import woowacourse.movie.ui.ScreenDetailUi

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

fun ScreenAndAd.Screen.toDetailUi(): ScreenDetailUi =
    ScreenDetailUi(
        id = id,
        dateRange = dateRange,
        movieDetailUI = movie.toDetailUi(),
    )

fun ScreenAndAd.Screen.toData(): ScreenData =
    ScreenData(
        id = id,
        movieData = movie.toData(),
        dateRange = dateRange,
    )
