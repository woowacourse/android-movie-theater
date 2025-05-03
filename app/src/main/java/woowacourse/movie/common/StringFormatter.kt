package woowacourse.movie.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object StringFormatter {
    @JvmStatic
    fun date(date: LocalDate): String = date.format(DATE_FORMAT)

    private val DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd")
}
