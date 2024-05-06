package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.Ticket.Companion.MAX_TICKET_COUNT
import woowacourse.movie.presentation.model.Ticket.Companion.MIN_TICKET_COUNT

class ScreenDetailPresenter(
    private val view: ScreenDetailContract.View,
    private val repository: ScreenRepository,
) : ScreenDetailContract.Presenter {
    override fun loadScreenDetail(
        movieId: Int,
        theaterId: Int,
    ) {
        repository.findByScreenId(movieId = movieId, theaterId = theaterId).onSuccess { screen ->
            val screenDetail =
                ScreenDetailUiModel(
                    screenId = screen.id,
                    theaterId = theaterId,
                    screen = screen,
                    selectableDates = screen.selectableDates,
                    selectedDate = screen.selectableDates.first(),
                    selectedTime = screen.selectableDates.first().getSelectableTimes().first(),
                )
            view.showScreenDetail(screenDetail)
        }.onFailure { e ->
            showLoadingScreenFailed(e)
        }
    }

    private fun showLoadingScreenFailed(e: Throwable) {
        when (e) {
            is NoSuchElementException -> {
                view.showToastMessage(e)
                view.back()
            }

            else -> {
                view.showToastMessage(e)
                view.back()
            }
        }
    }

    override fun plusTicket(ticket: Ticket) {
        val newTicket = ticket.increase(1)
        if (newTicket.isInvalidCount()) {
            view.showSnackBar(MessageType.TicketMaxCountMessage(MAX_TICKET_COUNT))
            return
        }
        view.updateTicketCount(newTicket)
    }

    override fun minusTicket(ticket: Ticket) {
        val newTicket = ticket.decrease(1)
        if (newTicket.isInvalidCount()) {
            view.showSnackBar(MessageType.TicketMinCountMessage(MIN_TICKET_COUNT))
            return
        }
        view.updateTicketCount(newTicket)
    }
}
