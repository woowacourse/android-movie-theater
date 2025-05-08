package woowacourse.movie.view.reservelist

import woowacourse.movie.domain.model.Ticket

interface ReservationListContract {
    interface View {
        fun showReservationList(data: List<Ticket>)
    }

    interface Presenter {
        fun loadData()
    }
}
