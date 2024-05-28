package woowacourse.movie.ui.reservation

import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.model.ReservationTicket
import woowacourse.movie.data.repository.NotificationRepository
import woowacourse.movie.data.repository.PreferenceRepository
import woowacourse.movie.data.repository.ReservationRepository
import java.time.LocalDateTime
import kotlin.concurrent.thread

class ReservationCompletePresenter(
    private val view: ReservationCompleteContract.View,
    private val reservationRepository: ReservationRepository,
    private val preferenceRepository: PreferenceRepository,
    private val notificationRepository: NotificationRepository,
    private val reservationTicketId: Int = 1,
) : ReservationCompleteContract.Presenter {
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun loadReservation() {
        thread {
            reservationRepository.findById(reservationTicketId)
                .onSuccess { reservationTicket ->
                    registerNotification(reservationTicket)
                    uiHandler.post {
                        view.showReservation(reservationTicket)
                    }
                }
                .onFailure { e ->
                    view.showReservationFail(e)
                }
        }
    }

    override fun updateNotificationPreference(isGranted: Boolean) {
        preferenceRepository.saveNotificationPreference(isGranted)
    }

    private fun registerNotification(reservationTicket: ReservationTicket) {
        val dateTIme = LocalDateTime.of(reservationTicket.date, reservationTicket.time)
        notificationRepository.register(reservationTicket.id, dateTIme)
    }
}
