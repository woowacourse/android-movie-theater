package woowacourse.movie.domain.model

import java.time.LocalDate
import java.time.LocalTime

class ScreeningDateTime(
    val screeningDate: LocalDate,
    val screeningTime: LocalTime,
) {
    fun isScreening(
        selectedDate: LocalDate,
        selectedTime: LocalTime,
    ): Boolean {
        return screeningDate.isBefore(selectedDate).not() && screeningTime.isBefore(selectedTime).not()
    }
}
