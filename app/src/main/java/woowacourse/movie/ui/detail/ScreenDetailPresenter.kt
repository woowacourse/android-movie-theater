package woowacourse.movie.ui.detail

import android.util.Log
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.Ticket.Companion.MIN_TICKET_COUNT
import woowacourse.movie.domain.model.WeeklyScreenTimePolicy
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.ui.toDetailUI
import java.lang.IllegalStateException
import java.time.LocalDate

class ScreenDetailPresenter(
    private val view: ScreenDetailContract.View,
    private val movieRepository: MovieRepository,
    private val screenRepository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
    private val screenTimePolicy: WeeklyScreenTimePolicy = WeeklyScreenTimePolicy(),
) : ScreenDetailContract.Presenter {
    private var ticket: Ticket = Ticket(MIN_TICKET_COUNT)
    private var dateRange = DateRange(LocalDate.now(), LocalDate.now())
    private var datePosition: Int = 0
    private var timePosition: Int = 0

    private var screenId: Int = -1
    private var theaterId: Int = -1

    override fun saveId(
        screenId: Int,
        theaterId: Int,
    ) {
        this.screenId = screenId
        this.theaterId = theaterId
    }

    override fun loadScreen() {
        try {
            val loadedScreen = screen(screenId)
            view.showScreen(loadedScreen.toDetailUI(movieRepository.imageSrc(screen(screenId).movie.id)))
            dateRange = loadedScreen.dateRange
            view.showDateTimePicker(dateRange, screenTimePolicy, ::saveDatePosition, ::saveTimePosition)
        } catch (e: Exception) {
            when (e) {
                is NoSuchElementException -> view.goToBack(e)
                else -> view.unexpectedFinish(e)
            }
        }
    }

    override fun loadTicket() {
        view.showTicket(ticket.count)
    }

    override fun saveDatePosition(datePosition: Int) {
        Log.d(TAG, "saveDatePosition: $datePosition")
        this.datePosition = datePosition
    }

    override fun saveTimePosition(timePosition: Int) {
        Log.d(TAG, "saveTimePosition: $timePosition")
        this.timePosition = timePosition
    }

    override fun saveTicket(count: Int) {
        ticket = Ticket(count)
    }

    private fun screen(id: Int): Screen {
        screenRepository.findById(id = id).onSuccess { screen ->
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
            view.showToastMessage(e)
        }
    }

    override fun minusTicket() {
        try {
            ticket = ticket.decrease()
            view.showTicket(ticket.count)
        } catch (e: IllegalArgumentException) {
            view.showToastMessage(e)
        }
    }

    override fun reserve() {
        reservationRepository.saveTimeReservation(
            screen(screenId),
            count = ticket.count,
            dateTime =
                DateTime(
                    dateRange.allDates()[datePosition],
                    screenTimePolicy.screeningTimes(dateRange.allDates()[datePosition])[timePosition],
                ),
        ).onSuccess { timeReservationId ->
            view.navigateToSeatsReservation(timeReservationId, theaterId)
        }.onFailure { e ->
            view.showToastMessage(e)
        }
    }

    companion object {
        private const val TAG = "ScreenDetailPresenter"
    }
}
