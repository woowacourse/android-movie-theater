package woowacourse.movie.domain.model.feed

import woowacourse.movie.domain.model.booking.ScreeningDates

sealed interface Feed {
    data class Movie(
        val id: Int,
        val title: String,
        val posterResource: String,
        val screeningDates: ScreeningDates,
        val runningTime: Int,
    ) : Feed

    data class Advertisement(
        val imgResource: String = "ad",
    ) : Feed
}
