package woowacourse.movie.model.replaced

@JvmInline
value class TicketCountState(val value: Int) {

    companion object {
        const val TICKET_COUNT_INTENT_KEY = "ticket_count"
        const val TICKET_COUNT_INSTANCE_KEY = "ticket_count"
    }
}
