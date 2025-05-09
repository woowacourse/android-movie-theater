package woowacourse.movie.view.reservelist

import woowacourse.movie.repository.TicketRepository

class ReservationListPresenter(
    val view: ReservationListContract.View,
    val repository: TicketRepository,
) : ReservationListContract.Presenter {
    override fun loadData() {
        repository.findAll()
            .onSuccess {
                view.showReservationList(it)
            }
            .onFailure {
                view.showReservationList(emptyList())
            }
    }
}
