package woowacourse.movie.view.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class LocalFormattedDate(val date: LocalDate) {
    override fun toString(): String {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return date.format(dateFormat)
    }
}
