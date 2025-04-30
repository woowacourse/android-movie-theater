package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

class ScreeningDateTime(
    val screeningDate: LocalDate,
    val screeningTime: LocalTime,
) : Serializable {
    fun isScreening(
        selectedDate: LocalDate,
        selectedTime: LocalTime,
    ): Boolean {
        return screeningDate.isBefore(selectedDate).not() && screeningTime.isBefore(selectedTime).not()
    }
}
