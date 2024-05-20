package woowacourse.movie.domain.model

import woowacourse.movie.data.model.ScreenData

sealed interface ScreenAndAd {
    data class Screen(
        val id: Int,
        val movie: Movie,
        val dateRange: DateRange,
    ) : ScreenAndAd

    data class Advertisement(
        val id: Int,
    ) : ScreenAndAd
}

fun ScreenData.toScreen(): ScreenAndAd.Screen =
    ScreenAndAd.Screen(
        id = id,
        movie = movie,
        dateRange = dateRange,
    )
