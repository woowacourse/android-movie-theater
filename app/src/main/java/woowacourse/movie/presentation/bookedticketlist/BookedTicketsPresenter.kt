package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.data.BookedTicketsData
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsPresenter(private val view: BookedTicketsContract.View) :
    BookedTicketsContract.Presenter {

    private lateinit var tickets: List<TicketModel>

    override fun changeTickets() {
        tickets = BookedTicketsData.tickets.toList()
        view.setBookedTicketsAdapter(BookedTicketsData.tickets)
    }
}
