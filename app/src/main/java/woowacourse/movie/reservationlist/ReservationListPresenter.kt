package woowacourse.movie.reservationlist

import android.util.Log
import woowacourse.movie.repository.MovieRepository
import kotlin.concurrent.thread

class ReservationListPresenter(
    val view: ReservationListContract.View,
    val repository: MovieRepository,
) : ReservationListContract.Presenter {
    override fun loadReservationList() {
        thread {
            runCatching {
                val reservations = repository.reservations()
                Log.d("테스트", "$reservations")
                reservations.map { it.toReservationUiModel(repository.theaterById(it.theaterId).name) }
            }.onSuccess {
                view.showReservationList(it)
            }
        }.join()
    }
}
