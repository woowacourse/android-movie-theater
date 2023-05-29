package woowacourse.movie.uimodel

import java.io.Serializable
import java.time.LocalDateTime

data class MovieTicketInfoModel(
    val title: String,
    val time: LocalDateTime,
    val peopleCount: PeopleCountModel,
) : Serializable
