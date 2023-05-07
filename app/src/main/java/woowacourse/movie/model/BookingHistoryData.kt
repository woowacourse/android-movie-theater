package woowacourse.movie.model

import java.io.Serializable

data class BookingHistoryData(
    val title: String,
    val date: String,
    val numberOfPeople: Int,
    val seat: List<String>,
    val price: String,
    val theater: String,
) : Serializable, TicketData
