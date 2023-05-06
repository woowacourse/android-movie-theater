package woowacourse.movie.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeFormatHelper {
    fun String.toLocalDateTime(): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(this, formatter);
    }
}
