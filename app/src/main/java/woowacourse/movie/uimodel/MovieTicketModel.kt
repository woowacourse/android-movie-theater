package woowacourse.movie.uimodel

import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MovieTicketModel(
    val title: String,
    val time: LocalDateTime,
    val peopleCount: PeopleCountModel,
    val seats: SelectedSeatsModel,
) : Serializable {
    fun getDateToString() = time.dateFormat()
    fun getTimeToString() = time.timeFormat()
    private fun LocalDateTime.dateFormat(): String = format(
        DateTimeFormatter.ofPattern("yyyy.MM.dd"),
    )

    private fun LocalDateTime.timeFormat(): String = format(
        DateTimeFormatter.ofPattern("HH:mm"),
    )
}
