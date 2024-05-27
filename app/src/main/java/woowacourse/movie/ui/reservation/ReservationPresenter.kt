package woowacourse.movie.ui.reservation

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.channels.ticker
import woowacourse.movie.data.repository.PreferenceRepository
import woowacourse.movie.data.repository.ReservationRepository
import kotlin.concurrent.thread

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationRepository: ReservationRepository,
    private val preferenceRepository: PreferenceRepository,
    private val reservationTicketId: Int = 1,
) : ReservationContract.Presenter {
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun loadReservation() {
        Log.d(TAG, "loadReservation: called")
        thread {
            reservationRepository.findById(reservationTicketId).also { Log.d(TAG, "loadReservation: it $it") }
                .onSuccess { reservationTicket ->
                    uiHandler.post {
                        Log.d(TAG, "loadReservation: onSuccess reservationTicket: $reservationTicket")
                        view.showReservation(reservationTicket)
                    }
                }
                .onFailure { e ->
                    Log.d(TAG, "loadReservation: onFailure reservationTicket: $reservationTicketId")
                    view.showReservationFail(e)
                }
        }
    }

    override fun updateNotificationPreference(isGranted: Boolean) {
        preferenceRepository.saveNotificationPreference(isGranted)
    }

    companion object {
        private const val TAG = "ReservationPresenter"
    }
}
