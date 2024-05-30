package woowacourse.movie.domain.model

import woowacourse.movie.data.model.ScreenData

data class TimeReservation(
    val id: Int,
    val screenData: ScreenData,
    val ticket: Ticket,
    val dateTime: DateTime,
) {
    companion object {
        val NULL = TimeReservation(0, ScreenData.NULL, Ticket(1), DateTime.NULL)
    }
}
