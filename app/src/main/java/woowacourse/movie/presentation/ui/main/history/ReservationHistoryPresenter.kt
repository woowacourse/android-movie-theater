package woowacourse.movie.presentation.ui.main.history

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.movie.domain.repository.ReservationRepository

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val repository: ReservationRepository,
) : ReservationHistoryContract.Presenter {
    override fun loadReservations() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.findReservations().onSuccess { reservations ->
                withContext(Dispatchers.Main) {
                    view.showReservations(reservations)
                }
            }.onFailure { e ->
                Log.d("Ttt e", e.toString())
                withContext(Dispatchers.Main) {
                    view.showToastMessage(e)
                }
            }
        }
    }

    override fun onReservationClick(id: Long) {
        view.navigateToReservation(id)
    }
}
