package woowacourse.movie.reservationlist

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
                reservations.map { it.toReservationUiModel(repository.theaterById(it.theaterId).name) }
            }.onSuccess {
                view.showReservationList(it)
            }
        }.join()
    }
}
