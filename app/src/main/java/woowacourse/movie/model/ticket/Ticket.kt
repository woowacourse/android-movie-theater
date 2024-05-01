package woowacourse.movie.model.ticket

import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats
import java.io.Serializable

class Ticket(
    count: Int = DEFAULT_TICKET_COUNT,
    screeningDateTime: ScreeningDateTime = ScreeningDateTime("", ""),
) : Serializable {
    var count: Int = count
        private set
    var screeningDateTime = screeningDateTime
        private set

    private fun restoreCount(recordOfCount: Int) {
        count = recordOfCount
    }

    fun restoreTicket(count: Int): Ticket {
        restoreCount(count)
        return this
    }

    fun calculateAmount(seats: Seats): Int {
        return seats.seats.sumOf { it.grade.price }
    }

    companion object {
        private const val DEFAULT_TICKET_COUNT = 1
    }
}
