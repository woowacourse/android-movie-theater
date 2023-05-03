package woowacourse.movie.uimodel

import java.io.Serializable
import java.time.LocalDateTime

data class MovieTicketModel(
    val title: String,
    val time: LocalDateTime,
    val peopleCount: PeopleCountModel,
    val seats: SelectedSeatsModel,
) : Serializable
