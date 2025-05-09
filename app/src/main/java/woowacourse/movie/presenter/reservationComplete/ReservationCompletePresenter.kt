package woowacourse.movie.presenter.reservationComplete

import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.entity.MovieTicketEntity
import kotlin.concurrent.thread

class ReservationCompletePresenter(
    private val view: ReservationCompleteContracts.View,
    private val database: AppDatabase,
) : ReservationCompleteContracts.Presenter {
    override fun updateTicketData(reservationDetailId: Long) {
        thread {
            val movieTicket: MovieTicketEntity =
                database.reservationDao().getMovieTicketByReservationId(reservationDetailId)
                    ?: return@thread
            view.showMovieTicket(movieTicket)
        }
    }
}
