package woowacourse.movie.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeFormatUtil {
    @JvmStatic
    fun dateFormat(
        localDateTime: LocalDateTime,
        format: String,
    ): String {
        val dateFormat = DateTimeFormatter.ofPattern(format)
        return dateFormat.format(localDateTime)
    }

    @JvmStatic
    fun timeFormat(
        localDateTime: LocalDateTime,
        format: String,
    ): String {
        val timeFormatter = DateTimeFormatter.ofPattern(format)
        return timeFormatter.format(localDateTime)
    }
}
