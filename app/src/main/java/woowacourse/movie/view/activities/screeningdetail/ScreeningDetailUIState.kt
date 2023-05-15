package woowacourse.movie.view.activities.screeningdetail

import androidx.annotation.DrawableRes
import woowacourse.movie.domain.screening.Screening
import woowacourse.movie.domain.screening.ScreeningRange
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.view.PosterResourceProvider
import java.time.LocalDate
import java.time.LocalTime

data class ScreeningDetailUIState(
    @DrawableRes val poster: Int,
    val title: String,
    val screeningStartDate: LocalDate,
    val screeningEndDate: LocalDate,
    val runningTime: Int,
    val summary: String,
    val screeningDateTimes: Map<LocalDate, List<LocalTime>>,
    val maxAudienceCount: Int,
    val screeningId: Long
) {

    companion object {
        fun of(screening: Screening, theater: Theater): ScreeningDetailUIState {
            val movie = screening.movie
            val screeningId = screening.id
            requireNotNull(screeningId) { "상영의 아이디가 널이면 UI 상태를 생성할 수 없습니다." }

            return ScreeningDetailUIState(
                PosterResourceProvider.getPosterResourceId(screening),
                movie.title,
                screening.screeningRange.startDate,
                screening.screeningRange.endDate,
                movie.runningTime.value,
                movie.summary,
                createScreeningDateTimes(
                    screening.screeningRange,
                    screening.timeTable.timeTable[theater] ?: listOf()
                ),
                theater.seats.size,
                screeningId
            )
        }

        private fun createScreeningDateTimes(
            screeningRange: ScreeningRange,
            times: List<LocalTime>
        ): Map<LocalDate, List<LocalTime>> {
            val result = mutableMapOf<LocalDate, List<LocalTime>>()
            var date = screeningRange.startDate
            while (date <= screeningRange.endDate) {
                result[date] = times
                date = date.plusDays(1)
            }
            return result
        }
    }
}
