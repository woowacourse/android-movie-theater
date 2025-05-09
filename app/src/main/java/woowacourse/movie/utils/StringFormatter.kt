package woowacourse.movie.utils

import android.icu.text.DecimalFormat
import woowacourse.movie.domain.model.movie.ScreeningPeriod
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object StringFormatter {
    fun periodFormat(screeningPeriod: ScreeningPeriod): String =
        PERIOD_FORMAT.format(
            dotDateFormat(screeningPeriod.startDate),
            dotDateFormat(screeningPeriod.endDate),
        )

    fun dotDateFormat(time: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_TIME_DOT_FORMAT)
        return time.format(formatter)
    }

    fun thousandFormat(price: Int): String = DecimalFormat(THOUSAND_UNIT_FORMAT).format(price)

    fun dateTimeFormat(localDateTime: LocalDateTime): String = localDateTime.format(DateTimeFormatter.ofPattern("yyyy.M.d HH:mm"))

    private const val PERIOD_FORMAT = "%s ~ %s"
    private const val DATE_TIME_DOT_FORMAT = "yyyy.M.d"
    private const val THOUSAND_UNIT_FORMAT = "#,###"
}
