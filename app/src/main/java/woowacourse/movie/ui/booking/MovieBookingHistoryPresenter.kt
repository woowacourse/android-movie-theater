package woowacourse.movie.ui.booking

import woowacourse.movie.model.db.UserTicket
import woowacourse.movie.model.db.UserTicketRepository
import kotlin.concurrent.thread

class MovieBookingHistoryPresenter(
    private val view: MovieBookingHistoryContract.View,
    private val userTicketRepository: UserTicketRepository,
) :
    MovieBookingHistoryContract.Presenter {
    override fun loadBookingHistories() {
        var userTickets: List<UserTicket> = emptyList()
        thread {
            userTickets = userTicketRepository.findAll()
        }.join()
        view.showBookingHistories(userTickets)
    }
}
