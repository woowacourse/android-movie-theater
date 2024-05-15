package woowacourse.movie.presentation.main.history

import woowacourse.movie.domain.repository.MovieTicketRepository

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val movieTicketRepository: MovieTicketRepository
) : ReservationHistoryContract.Presenter {
    override fun loadReservationHistory() {
        val movieTickets = movieTicketRepository.findAll()
        view.showReservationHistory(movieTickets)
    }

    override fun itemClicked(movieTicketId: Long) {
        view.navigateToReservationActivity(movieTicketId)
    }
}
