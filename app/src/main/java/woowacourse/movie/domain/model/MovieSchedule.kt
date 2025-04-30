package woowacourse.movie.domain.model

import java.time.LocalDate
import java.time.LocalTime

class MovieSchedule(
    val movie: Movie,
    val screeningDateTime: ScreeningDateTime,
    val seat: Seats,
) {
    fun isScreeningByDateAndTime(
        selectedMovie: Movie,
        selectedDate: LocalDate,
        selectedTime: LocalTime,
    ): Boolean {
        return movie == selectedMovie && screeningDateTime.isScreening(selectedDate, selectedTime)
    }
}
