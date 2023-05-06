package woowacourse.movie.view.activities.home.fragments.reservationlist

interface ReservationListContract {

    interface Presenter {
        fun loadReservations()
    }

    interface View {
        fun setReservationList(reservationListViewItemUIStates: List<ReservationListViewItemUIState>)
    }
}
