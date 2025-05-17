package woowacourse.movie.ui.complete

import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.BookedTicketRepository
import woowacourse.movie.providers.BookedTicketRepositoryProvider

class BookingCompletePresenter(
    private val bookingCompleteView: BookingCompleteContract.View,
) : BookingCompleteContract.Presenter {
    private val repository: BookedTicketRepository by lazy { BookedTicketRepositoryProvider.provideBookedTicketRepository() }
    private lateinit var bookedTicket: BookedTicket

    override fun loadBookedTicket(bookedTicketId: Long) {
        repository.fetchById(bookedTicketId) { bookedTicket ->
            this.bookedTicket = bookedTicket
            bookingCompleteView.showBookedTicket(bookedTicket)
        }
    }
}
