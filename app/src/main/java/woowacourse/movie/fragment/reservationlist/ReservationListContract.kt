package woowacourse.movie.fragment.reservationlist

import woowacourse.movie.view.data.ReservationsViewData

interface ReservationListContract {

    interface View {
        var presenter: Presenter
        fun setReservationData(reservationsViewData: ReservationsViewData)
    }

    interface Presenter {
        fun loadReservationData()
    }
}
