package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

class MovieSchedule(
    val movie: Movie,
    val screeningDateTime: ScreeningDateTime,
    val seat: Seats,
) : Serializable {
    fun isScreeningByDateAndTime(
        selectedMovie: Movie,
        selectedDate: LocalDate,
        selectedTime: LocalTime,
    ): Boolean {
        return movie == selectedMovie && screeningDateTime.isScreening(selectedDate, selectedTime)
    }
}
