package woowacourse.movie.booking.complete

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.movie.booking.complete.alarm.AlarmScheduler
import woowacourse.movie.reservation.ReservationRepository
import woowacourse.movie.ui.model.TicketUiModel

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val reservationRepository: ReservationRepository,
    private val alarmScheduler: AlarmScheduler,
) : BookingCompleteContract.Presenter {
    private lateinit var ticket: TicketUiModel

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket
        view.showBookingCompleteResult(ticket)
    }

    override fun saveReservation(
        ticket: TicketUiModel,
        bookingType: String,
    ) {
        if (bookingType == BookingType.RESERVATION.name) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    reservationRepository.insertReservation(ticket)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        view.showToastErrorAndFinish(ERROR_RESERVATION_TOAST_MESSAGE)
                    }
                }
            }
        }
    }

    override fun setNotification(
        ticket: TicketUiModel,
        bookingType: String,
    ) {
        alarmScheduler.scheduleAlarm(bookingType, ticket)
    }

    companion object {
        private const val ERROR_RESERVATION_TOAST_MESSAGE = "예약 중 오류가 발생했습니다."
    }
}
