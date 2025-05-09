package woowacourse.movie.view.reservelist

import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.Repository

class ReservationListPresenter(
    val view: ReservationListContract.View,
    val repository: Repository<Ticket>,
) : ReservationListContract.Presenter {
    override fun loadData() {
        repository.findAll()?.let {
            view.showReservationList(it)
        } ?: view.showReservationList(emptyList())
    }
}
