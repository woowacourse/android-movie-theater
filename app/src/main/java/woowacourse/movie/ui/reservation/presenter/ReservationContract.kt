package woowacourse.movie.ui.reservation.presenter

interface ReservationContract {
    interface View {
        val presenter: Presenter
        fun setTextOnEmptyState(isEmpty: Boolean)
    }

    interface Presenter {
        fun isEmptyMovieReservation()
    }
}
