package woowacourse.movie.presentation.ticket

import woowacourse.movie.domain.repository.MovieTicketRepository

class MovieTicketPresenter(
    val view: MovieTicketContract.View,
    private val movieTicketRepository: MovieTicketRepository,
    private val movieTicketId: Long
) : MovieTicketContract.Presenter {
    init {
        if (movieTicketId <= INVALID_MOVIE_TICKET_ID) {
            view.showMessage(ERROR_MESSAGE)
        } else {
            loadReservationDetails()
        }
    }

    override fun loadReservationDetails() {
        runCatching {
            movieTicketRepository.getMovieTicket(movieTicketId)
        }.onSuccess { ticket ->
            view.showTicketData(ticket)
        }.onFailure {
            view.showMessage(ERROR_MESSAGE.format(it.message))
        }
    }

    companion object {
        const val INVALID_MOVIE_TICKET_ID = -1
        const val ERROR_MESSAGE = "예매 정보를 불러오는데 실패했습니다. %s"
    }
}
