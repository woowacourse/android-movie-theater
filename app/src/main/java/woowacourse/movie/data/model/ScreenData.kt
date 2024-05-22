package woowacourse.movie.data.model

import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.ScreenTimePolicy
import java.time.LocalDate
import java.time.LocalTime

data class ScreenData(
    val id: Int,
    val movieData: MovieData,
    val dateRange: DateRange = DateRange(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)),
) {
    fun allScreeningTime(policy: ScreenTimePolicy): List<LocalTime> {
        return dateRange.allDates().flatMap {
            policy.screeningTimes(it)
        }
    }

    companion object {
        val NULL =
            ScreenData(
                id = -1,
                movieData = MovieData.FAKE,
            )
    }
}
