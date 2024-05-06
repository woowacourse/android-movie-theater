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
    private var uiModel = DetailUiModel()
    val count: Int get() = uiModel.ticket.count
    val selectableDates: List<ScreenDate> get() = uiModel.selectableDates
    val selectedDate: ScreenDate? get() = uiModel.selectedDate
    val date: LocalDate? get() = uiModel.selectedDate?.date
    val selectedTime: LocalTime? get() = uiModel.selectedTime

    override fun loadScreen(
        movieId: Int,
        theaterId: Int,
    ) {
        repository.findByScreenId(movieId = movieId, theaterId = theaterId).onSuccess { screen ->
            uiModel =
                uiModel.copy(
                    screenId = screen.id,
                    theaterId = theaterId,
                    screen = screen,
                    selectableDates = screen.selectableDates,
                    selectedDate = screen.selectableDates.first(),
                    selectedTime = screen.selectableDates.first().getSelectableTimes().first(),
                )
            view.showScreen(screen)
            view.showTicket(uiModel.ticket.count)
        }.onFailure { e ->
            when (e) {
                is NoSuchElementException -> {
                    view.showToastMessage(e)
                    view.navigateBackToPrevious()
                }

                else -> {
                    view.showToastMessage(e)
                    view.navigateBackToPrevious()
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
        uiModel = uiModel.copy(selectedDate = ScreenDate(date))
    }

    override fun registerTime(time: LocalTime) {
        uiModel = uiModel.copy(selectedTime = time)
    }

    override fun updateTicket(count: Int) {
        uiModel = uiModel.copy(ticket = Ticket(count))
        view.showTicket(count)
    }

    override fun plusTicket() {
        val nextTicket = uiModel.ticket.increase(1)

        if (nextTicket.isInvalidCount()) {
            view.showSnackBar(MessageType.TicketMaxCountMessage(MAX_TICKET_COUNT))
            return
        }
        uiModel = uiModel.copy(ticket = nextTicket)
        view.showTicket(uiModel.ticket.count)
    }

    override fun minusTicket() {
        val nextTicket = uiModel.ticket.decrease(1)

        if (nextTicket.isInvalidCount()) {
            view.showSnackBar(MessageType.TicketMinCountMessage(MIN_TICKET_COUNT))
            return
        }
        uiModel = uiModel.copy(ticket = nextTicket)
        view.showTicket(uiModel.ticket.count)
    }

    override fun selectSeat() {
        uiModel.selectedDate?.let { selectedDate ->
            val reservationInfo =
                ReservationInfo(
                    theaterId = uiModel.theaterId,
                    dateTime = selectedDate.getLocalDateTime(uiModel.selectedTime),
                    ticketCount = uiModel.ticket.count,
                )
            view.navigateToSeatSelection(reservationInfo)
        }
    }
}
