package woowacourse.movie.utils

import android.icu.text.DecimalFormat
import woowacourse.movie.domain.model.ScreeningPeriod
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object StringFormatter {
    private const val PERIOD_FORMAT = "%s ~ %s"
    private const val DATE_FORMAT = "yyyy.M.d"
    private const val TIME_FORMAT = "HH:mm"
    private const val THOUSAND_UNIT_FORMAT = "#,###"

    fun periodFormat(screeningPeriod: ScreeningPeriod): String =
        PERIOD_FORMAT.format(
            dotDateFormat(screeningPeriod.startDate),
            dotDateFormat(screeningPeriod.endDate),
        )

    fun thousandFormat(price: Int): String = DecimalFormat(THOUSAND_UNIT_FORMAT).format(price)

    fun dateTimeFormat(localDateTime: LocalDateTime): String =
        localDateTime.format(DateTimeFormatter.ofPattern("$DATE_FORMAT $TIME_FORMAT"))

    fun toDate(localDateTime: LocalDateTime): String = localDateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT))

    fun toTime(localDateTime: LocalDateTime): String = localDateTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT))

    private fun dotDateFormat(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
    }
}
