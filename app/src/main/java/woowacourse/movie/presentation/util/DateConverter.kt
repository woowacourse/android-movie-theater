package woowacourse.movie.presentation.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDate.dateToString(): String = this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

fun LocalTime.timeToString(): String = this.format(DateTimeFormatter.ofPattern("HH:mm"))

fun LocalDateTime.dateToString(): String = this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
