package woowacourse.domain.movie

import java.time.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val screeningPeriod: ScreeningPeriod,
    val runningTime: Int,
    val description: String,
) {
    val screeningDates: List<LocalDate>
        get() = screeningPeriod.getScreeningDates().map { it.value }
}
