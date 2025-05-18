package woowacourse.movie.view.reservelist

import woowacourse.movie.repository.TicketRepository

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val repository: TicketRepository,
) : ReservationListContract.Presenter {
    override fun loadData() {
        repository.findAll {
            it.onSuccess {
                view.showReservationList(it)
            }
            it.onFailure {
                view.showReservationList(emptyList())
            }
        }
    }
}
