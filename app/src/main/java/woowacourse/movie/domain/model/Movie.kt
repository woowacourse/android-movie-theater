package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.data.DummyScreening
import java.time.LocalDate
import java.time.LocalDateTime

@Parcelize
data class Movie(
    val poster: String,
    val title: String,
    val runningTime: RunningTime,
    val startDate: LocalDate,
    val endDate: LocalDate,
) : Parcelable {
    val screening: List<Screening>
        get() {
            return findByMovie(this)
        }

    fun availableDates(now: LocalDateTime): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var date = now.toLocalDate()
        while (!date.isAfter(endDate)) {
            dates.add(date)
            date = date.plusDays(1)
        }
        return dates.filterNot { it.isBefore(startDate) }
    }

    private fun findByMovie(movie: Movie): List<Screening> {
        return DummyScreening.dummyScreenings.filter { it.movie == movie }
    }
}
