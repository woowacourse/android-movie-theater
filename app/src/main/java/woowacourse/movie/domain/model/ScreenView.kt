package woowacourse.movie.domain.model

import java.time.LocalDate

sealed interface ScreenView {
    data class Movie(
        val id: Int,
        val title: String,
        val runningTime: Int,
        val imageSrc: Int,
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : ScreenView

    data class Ads(val poster: Int) : ScreenView
}
