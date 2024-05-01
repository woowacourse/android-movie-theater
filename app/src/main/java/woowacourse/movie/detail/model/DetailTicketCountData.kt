package woowacourse.movie.detail.model

object DetailTicketCountData {
    private const val MIN_TICKET_COUNT = 1

    var ticketCount = Count(MIN_TICKET_COUNT)
        private set

    fun initTicketCount(count: Int) {
        ticketCount = Count(count)
    }

    fun minusTicketCount() {
        ticketCount--
    }

    fun plusTicketCount() {
        ticketCount++
    }
}
