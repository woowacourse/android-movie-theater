package woowacourse.movie.ui.detail

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.repository.ReservationRepository
import woowacourse.movie.data.source.MovieDataSource
import woowacourse.movie.data.source.ScreenDataSource
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.ScreenTimePolicy
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.Ticket.Companion.MIN_TICKET_COUNT
import woowacourse.movie.domain.model.WeeklyScreenTimePolicy
import woowacourse.movie.ui.toDetailUI

class ScreenDetailPresenter(
    private val view: ScreenDetailContract.View,
    private val movieDataSource: MovieDataSource,
    private val screenDataSource: ScreenDataSource,
    private val reservationRepository: ReservationRepository,
    private val screenTimePolicy: ScreenTimePolicy = WeeklyScreenTimePolicy(),
    screenId: Int,
    private val theaterId: Int,
) : ScreenDetailContract.Presenter {
    private val loadedScreenData: ScreenData = loadedScreen(screenId)
    private val dateRange = loadedScreenData.dateRange
    private var ticket: Ticket = Ticket(MIN_TICKET_COUNT)

    private var datePosition: Int = 0
    private var timePosition: Int = 0

    override fun loadScreen() {
        try {
            view.showScreen(loadedScreenData.toDetailUI(movieDataSource.imageSrc(loadedScreenData.movieData.id)))
            view.showDateTimePicker(dateRange, screenTimePolicy, view::showDate, view::showTime)
        } catch (e: Exception) {
            view.showScreenFail(e)
        }
    }

    override fun loadTicket() {
        view.showTicket(ticket.count)
    }

    override fun saveDatePosition(datePosition: Int) {
        this.datePosition = datePosition
    }

    override fun saveTimePosition(timePosition: Int) {
        this.timePosition = timePosition
    }

    override fun saveTicket(count: Int) {
        ticket = Ticket(count)
    }

    private fun loadedScreen(id: Int): ScreenData {
        screenDataSource.findById(id = id).onSuccess { screen ->
            return screen
        }.onFailure { e ->
            throw e
        }
        throw IllegalStateException("예기치 못한 오류")
    }

    override fun plusTicket() {
        try {
            ticket = ticket.increase()
            view.showTicket(ticket.count)
        } catch (e: IllegalArgumentException) {
            view.showTicketFail(e)
        }
    }

    override fun minusTicket() {
        try {
            ticket = ticket.decrease()
            view.showTicket(ticket.count)
        } catch (e: IllegalArgumentException) {
            view.showTicketFail(e)
        }
    }

    override fun reserve() {
        val date = dateRange.allDates()[datePosition]

        reservationRepository.savedTimeReservationId(
            loadedScreenData,
            count = ticket.count,
            dateTime =
                DateTime(
                    date = date,
                    time = screenTimePolicy.screeningTimes(date)[timePosition],
                ),
        ).onSuccess { timeReservationId ->
            view.showSeatsReservation(timeReservationId, theaterId)
        }.onFailure { e ->
            view.showScreenFail(e)
        }
    }
}
