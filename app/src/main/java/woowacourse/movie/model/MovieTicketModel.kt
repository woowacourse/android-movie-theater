package woowacourse.movie.model

import woowacourse.movie.ui.seat.uimodel.SelectedSeatsModel
import java.io.Serializable
import java.time.LocalDateTime

data class MovieTicketModel(
    val title: String,
    val time: LocalDateTime,
    val peopleCount: PeopleCountModel,
    val seats: SelectedSeatsModel,
) : Serializable
