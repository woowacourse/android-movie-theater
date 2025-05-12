package woowacourse.movie.model.ticket

import java.io.Serializable

@JvmInline
value class TicketCount(
    val value: Int = TICKET_MINIMUM_COUNT,
) : Serializable {
    operator fun plus(other: Int): TicketCount = TicketCount(value + other)

    operator fun minus(other: Int): TicketCount {
        if (value - other <= TICKET_MINIMUM_COUNT) return TicketCount()
        return TicketCount(value - other)
    }

    companion object {
        private const val TICKET_MINIMUM_COUNT = 1
    }
}
