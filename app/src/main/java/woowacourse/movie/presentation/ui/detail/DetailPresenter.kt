package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.Ticket.Companion.MAX_TICKET_COUNT
import woowacourse.movie.presentation.model.Ticket.Companion.MIN_TICKET_COUNT
import java.time.LocalDate
import java.time.LocalTime

class DetailPresenter(
    private val view: DetailContract.View,
    private val repository: ScreenRepository,
) : DetailContract.Presenter {
    private var _detailModel = DetailUiModel()
    val detailModel: DetailUiModel
        get() = _detailModel

    override fun loadScreen(
        movieId: Int,
        theaterId: Int,
    ) {
        repository.findByScreenId(movieId = movieId, theaterId = theaterId).onSuccess { screen ->
            _detailModel =
                detailModel.copy(
                    screenId = screen.id,
                    theaterId = theaterId,
                    screen = screen,
                    selectableDates = screen.selectableDates,
                    selectedDate = screen.selectableDates.first(),
                    selectedTime = screen.selectableDates.first().getSelectableTimes().first(),
                )
            view.showScreen(screen)
            view.showTicket(detailModel.ticket.count)
        }.onFailure { e ->
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
    }

    override fun createDateSpinnerAdapter(screenDates: List<ScreenDate>) {
        view.showDateSpinnerAdapter(screenDates)
    }

    override fun createTimeSpinnerAdapter(screenDate: ScreenDate) {
        view.showTimeSpinnerAdapter(screenDate)
    }

    override fun registerDate(date: LocalDate) {
        _detailModel = detailModel.copy(selectedDate = ScreenDate(date))
    }

    override fun registerTime(time: LocalTime) {
        _detailModel = detailModel.copy(selectedTime = time)
    }

    override fun updateTicket(count: Int) {
        _detailModel = detailModel.copy(ticket = Ticket(count))
        view.showTicket(count)
    }

    override fun plusTicket() {
        val nextTicket = detailModel.ticket.increase(1)

        if (nextTicket.isInvalidCount()) {
            view.showSnackBar(MessageType.TicketMaxCountMessage(MAX_TICKET_COUNT))
            return
        }
        _detailModel = detailModel.copy(ticket = nextTicket)
        view.showTicket(detailModel.ticket.count)
    }

    override fun minusTicket() {
        val nextTicket = detailModel.ticket.decrease(1)

        if (nextTicket.isInvalidCount()) {
            view.showSnackBar(MessageType.TicketMinCountMessage(MIN_TICKET_COUNT))
            return
        }
        _detailModel = detailModel.copy(ticket = nextTicket)
        view.showTicket(detailModel.ticket.count)
    }

    override fun selectSeat() {
        detailModel.selectedDate?.let { selectedDate ->
            val reservationInfo =
                ReservationInfo(
                    theaterId = detailModel.theaterId,
                    dateTime = selectedDate.getLocalDateTime(detailModel.selectedTime),
                    ticketCount = detailModel.ticket.count,
                )
            view.navigateToSeatSelection(reservationInfo)
        }
    }
}
