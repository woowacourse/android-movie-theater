package woowacourse.movie.reservation

import woowacourse.movie.database.AppDatabase
import woowacourse.movie.database.Ticket

interface ReservationContract {
    interface View {
        fun showReservations(reservations: List<Ticket>)

        fun showError(message: String)

        fun navigateToTicketDetail(ticket: Ticket)
    }

    interface Presenter {
        fun attachView(view: View)

        fun detachView()

        fun loadData(database: AppDatabase)

        fun onClickedList(ticket: Ticket)
    }
}
