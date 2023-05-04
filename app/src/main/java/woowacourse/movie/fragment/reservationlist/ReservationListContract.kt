package woowacourse.movie.fragment.reservationlist

import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.ReservationsViewData

interface ReservationListContract {

    interface View {
        var presenter: Presenter
        fun initReservationRecyclerView(reservationsViewData: ReservationsViewData)
        fun onItemClick(reservationViewData: ReservationViewData)
    }

    interface Presenter {
        fun initReservationRecyclerView()
        fun onItemClick(reservationViewData: ReservationViewData)
    }
}
