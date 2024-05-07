package woowacourse.movie.list.model

import woowacourse.movie.R
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed interface TheaterContent {
    val id: Long
}

data class Movie(
    val title: String,
    val posterResourceId: Int,
    val firstScreeningDate: LocalDate,
    val runningTime: Int,
    val description: String,
    override val id: Long,
) : TheaterContent, Serializable {
    val firstScreeningDateFormat: String
        get() = firstScreeningDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val runningTimeString
        get() = runningTime.toString()
}

data class Advertisement(
    val image: Int = R.drawable.advertisement,
    override val id: Long = -1,
) : TheaterContent
