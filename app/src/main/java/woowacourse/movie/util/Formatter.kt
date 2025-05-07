package woowacourse.movie.util

import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Formatter {
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.M.d")
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun formatDateDotSeparated(date: LocalDate): String = date.format(dateFormatter)

    fun formatStringDateDotSeparated(date: String): LocalDate = LocalDate.parse(date, dateFormatter)

    fun formatTimeWithMidnight24(time: LocalTime): String {
        return if (time == LocalTime.MIDNIGHT) {
            "24:00"
        } else {
            time.format(timeFormatter)
        }
    }

    fun formatStringTimeWithMidnight24(time: String): LocalTime {
        return LocalTime.parse(time, timeFormatter)
    }

    fun formatMoney(amount: Int): String = DecimalFormat("#,###").format(amount)
}
