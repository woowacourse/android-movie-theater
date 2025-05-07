package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class Screening(
    val screeningTimes: List<LocalTime>,
    val cinema: Cinema,
    val movie: Movie,
) : Parcelable {
    val availableTimesSize: Int get() = screeningTimes.size

    fun availableTimes(
        now: LocalDateTime,
        date: LocalDate,
    ): List<LocalTime> {
        return if (date == now.toLocalDate()) {
            screeningTimes.filter { it.isAfter(now.toLocalTime()) }
        } else {
            screeningTimes
        }
    }
}
