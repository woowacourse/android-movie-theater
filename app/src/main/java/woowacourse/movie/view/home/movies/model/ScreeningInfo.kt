package woowacourse.movie.view.home.movies.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ScreeningInfo(
    val movieId: Int,
    val theaterName: String,
    val screening: List<LocalDateTime>,
) : Serializable {
    fun screeningDate() = screening.map { it.toLocalDate() }

    fun screeningTime(selectedDate: LocalDate): List<LocalTime> {
        return screening
            .filter { it.toLocalDate() == selectedDate }
            .map { it.toLocalTime() }
    }
}
