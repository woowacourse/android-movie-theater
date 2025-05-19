package woowacourse.movie.domain.model.feed

import java.time.LocalDate

sealed interface Feed {
    data class Movie(
        val id: Int,
        val title: String,
        val posterResource: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val runningTime: Int,
    ) : Feed

    data class Ad(
        val imgResource: String = "ad",
    ) : Feed
}
