package woowacourse.movie.presentation.booking

interface BookingContract {
    interface View {
        val presenter: Presenter

        fun setTicketCount(ticketCount: Int)
    }

    interface Presenter {
        val view: View

        fun plusTicketCount()

        fun minusTicketCount()

        fun getTicketCurrentCount(): Int
    }
}
