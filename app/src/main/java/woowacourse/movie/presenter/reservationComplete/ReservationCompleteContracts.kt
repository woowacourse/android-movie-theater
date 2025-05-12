package woowacourse.movie.presenter.reservationComplete

import woowacourse.movie.model.ticket.MovieTicket

interface ReservationCompleteContracts {
    interface View {
        fun showMovieTicket(movieTicket: MovieTicket)

        fun showErrorDialogMessage()

        fun showAlarmPermissionScreen()

        fun showAlarmBeforeMovieStart(movieTicket: MovieTicket)
    }

    interface Presenter {
        fun updateTicketData(movieTicket: MovieTicket)

        fun requestErrorDialogMessage()

        fun requestAlarmPermissionScreen()

        fun requestAlarm(movieTicket: MovieTicket)
    }
}
