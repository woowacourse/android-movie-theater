package woowacourse.movie.data.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalFormattedDate(val date: LocalDate) {
    override fun toString(): String {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return date.format(dateFormat)
    }
}
