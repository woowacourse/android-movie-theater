package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class Screen(
    val id: Int,
    val movie: Movie,
    val dateRange: DateRange = DateRange(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)),
) : Serializable {
    fun allScreeningTime(policy: ScreenTimePolicy): List<LocalTime> {
        return dateRange.allDates().flatMap {
            policy.screeningTimes(it)
        }
    }

    companion object {
        val NULL =
            Screen(
                id = -1,
                movie = Movie.NULL,
            )
    }
}
