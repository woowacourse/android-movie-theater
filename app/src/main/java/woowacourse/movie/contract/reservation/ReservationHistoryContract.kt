package woowacourse.movie.contract.reservation

interface ReservationHistoryContract {
    interface Presenter {
        fun fetchReservationHistories()
    }

    interface View {
        fun updateReservationHistories()
    }
}
