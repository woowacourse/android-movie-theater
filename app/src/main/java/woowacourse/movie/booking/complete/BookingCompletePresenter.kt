package woowacourse.movie.booking.complete

import woowacourse.movie.booking.complete.alarm.AlarmScheduler
import woowacourse.movie.data.ReservationDao
import woowacourse.movie.mapper.toEntity
import woowacourse.movie.ui.model.TicketUiModel
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val reservationDao: ReservationDao,
    private val alarmScheduler: AlarmScheduler,
) : BookingCompleteContract.Presenter {
    private lateinit var ticket: TicketUiModel

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket
        view.showBookingCompleteResult(ticket)
    }

    override fun saveReservation(
        ticket: TicketUiModel,
        type: String,
    ) {
        thread {
            if (type == BookingType.RESERVATION.name) {
                reservationDao.insertReservation(ticket.toEntity())
            }
        }
    }

    override fun setNotification(
        ticket: TicketUiModel,
        bookingType: String,
    ) {
        alarmScheduler.scheduleAlarm(bookingType, ticket)
    }
}
