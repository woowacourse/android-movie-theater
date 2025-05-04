package woowacourse.movie.view.home.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ScreeningInfo(
    val movieId: Int,
    val theaterName: String,
    val screenings: List<LocalDateTime>,
) : Serializable {
    fun screeningTimes(selectedDate: LocalDate): List<LocalTime> {
        return screenings.filter { screening ->
            screening.toLocalDate() == selectedDate
        }.map { screening ->
            screening.toLocalTime()
        }
    }
}
