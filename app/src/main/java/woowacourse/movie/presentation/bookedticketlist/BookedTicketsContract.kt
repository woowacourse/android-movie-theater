package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.TicketModel

interface BookedTicketsContract {
    interface View {
        val presenter: Presenter
        fun setBookedTicketsAdapter(tickets: List<TicketModel>)
    }

    interface Presenter {
        fun changeTickets()
        fun getMovieModel(ticketModel: TicketModel): MovieModel
    }
}
