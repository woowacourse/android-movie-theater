package woowacourse.movie.ui.history

import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.BookedTicketRepository
import woowacourse.movie.providers.BookedTicketRepositoryProvider

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
) : BookingHistoryContract.Presenter {

    private val repository: BookedTicketRepository by lazy { BookedTicketRepositoryProvider.provideBookedTicketRepository() }

    override fun loadBookingHistories() {
        repository.fetchAll { bookedTickets ->
            view.showHistories(bookedTickets)
        }
    }

    override fun loadBookedTicket(bookedTicket: BookedTicket) {
        view.moveToBookedTicket(bookedTicket)
    }
}
