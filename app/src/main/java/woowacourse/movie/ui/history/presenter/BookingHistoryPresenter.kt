package woowacourse.movie.ui.history.presenter

import woowacourse.movie.data.mapper.BookedTicketMapper
import woowacourse.movie.data.repository.BookedTicketRepository
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.ui.history.contract.BookingHistoryContract
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val bookingHistoryView: BookingHistoryContract.View,
    private val bookedTicketRepository: BookedTicketRepository,
) : BookingHistoryContract.Presenter {
    private lateinit var bookedTickets: List<BookedTicket>

    override fun loadBookedTickets() {
        thread {
            val bookedTickets =
                bookedTicketRepository.getAll().map { BookedTicketMapper.toModel(it) }
            this.bookedTickets = bookedTickets
            bookingHistoryView.setBookedTicketItems(bookedTickets)
        }.join()
    }
}
