package woowacourse.movie.util

import java.time.LocalDateTime
import java.util.Calendar

fun LocalDateTime.toCalendar(): Calendar {
    return Calendar.getInstance().apply {
        set(
            year,
            monthValue - 1,
            dayOfMonth,
            hour,
            minute
        )
    }
}
