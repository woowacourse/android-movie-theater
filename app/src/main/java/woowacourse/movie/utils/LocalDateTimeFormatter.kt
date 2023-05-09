package woowacourse.movie.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDate.movieDetailFormat(): String = format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

fun LocalDateTime.reservationFormat(): String =
    format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))

fun String.getReservationLocalDateTime(): LocalDateTime = LocalDateTime.parse(this)
