package woowacourse.movie.presentation.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDate.formatDotDate(): String = format(DateTimeFormatter.ofPattern("YYYY.MM.dd"))

fun LocalDateTime.formatDotDateTimeColon(): String =
    format(DateTimeFormatter.ofPattern("YYYY.MM.dd HH:mm"))

fun LocalDateTime.formatDotDateTimeColonSeparateBar() =
    format(DateTimeFormatter.ofPattern("YYYY.MM.dd | HH:mm"))
