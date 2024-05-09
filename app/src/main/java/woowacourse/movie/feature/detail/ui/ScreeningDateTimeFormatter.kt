package woowacourse.movie.feature.detail.ui

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDate.formatSpinnerMessage(): String {
    return format(DateTimeFormatter.ofPattern(SCREENING_DATE_PATTERN))
}

fun LocalTime.formatSpinnerMessage(): String {
    return format(DateTimeFormatter.ofPattern(SCREENING_TIME_PATTERN))
}

fun String.unFormatSpinnerLocalDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(SCREENING_DATE_PATTERN)
    return LocalDate.parse(this, formatter)
}

fun String.unFormatSpinnerLocalTime(): LocalTime {
    val formatter = DateTimeFormatter.ofPattern(SCREENING_TIME_PATTERN)
    return LocalTime.parse(this, formatter)
}

private const val SCREENING_DATE_PATTERN = "yyyy-MM-dd"
private const val SCREENING_TIME_PATTERN = "HH:mm"
