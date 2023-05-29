package woowacourse.movie.uimodel

import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MovieTicketModel(
    private val movieTicketInfo: MovieTicketInfoModel,
    val seats: SelectedSeatsModel,
) : Serializable {
    val title: String
        get() = movieTicketInfo.title
    val time: LocalDateTime
        get() = movieTicketInfo.time
    val peopleCount: PeopleCountModel
        get() = movieTicketInfo.peopleCount

    fun getDateToString() = movieTicketInfo.time.dateFormat()
    fun getTimeToString() = movieTicketInfo.time.timeFormat()

    private fun LocalDateTime.dateFormat(): String = format(
        DateTimeFormatter.ofPattern("yyyy.MM.dd"),
    )

    private fun LocalDateTime.timeFormat(): String = format(
        DateTimeFormatter.ofPattern("HH:mm"),
    )
}
