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
    ): Boolean =
        screeningDate.isAfter(selectedDate) ||
            (
                screeningDate.isEqual(selectedDate) &&
                    screeningTime.isAfter(selectedTime)
            )
}
