package woowacourse.movie.model.movie

import java.io.Serializable

data class ScreeningNotification(
    val ticketId: Long,
    val title: String,
    val description: String,
) : Serializable
