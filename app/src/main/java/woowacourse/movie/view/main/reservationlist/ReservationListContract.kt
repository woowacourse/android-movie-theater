package woowacourse.movie.view.main.reservationlist

import woowacourse.movie.model.reservation.ReservationInfo

interface ReservationListContract {
    interface View {
        fun showReservationInfos(reservationInfos: List<ReservationInfo>)
    }

    interface Presenter {
        fun onViewCreated()

        fun onDestroyView()

        fun loadReservationInfos()

        fun getReservationInfos(): List<ReservationInfo>
    }
}
