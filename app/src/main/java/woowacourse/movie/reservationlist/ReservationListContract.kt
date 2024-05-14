package woowacourse.movie.reservationlist

interface ReservationListContract {
    interface View {
        fun showReservationList(reservations: List<ReservationListUiModel>)
    }

    interface Presenter {
        fun loadReservationList()
    }
}
