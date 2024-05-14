package woowacourse.movie.presentation.homefragments.reservationList

import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.model.Ticket
import woowacourse.movie.presentation.homefragments.reservationList.uiModel.ReservationItemUiModel

interface ReservationListContract {
    interface View {
        fun displayReservations(reservations: List<ReservationItemUiModel>)

        fun showErrorToast()

        fun navigate(ticket: Ticket)
    }

    interface Presenter {
        fun loadReservations(reservationDatabase: ReservationDatabase)

        fun navigate(
            reservationId: Long,
            reservationDatabase: ReservationDatabase,
        )
    }
}
