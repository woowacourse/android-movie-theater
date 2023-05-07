package woowacourse.movie.ui.reservation.presenter

import android.content.Context
import android.content.Intent
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.ReservationTicketMachine
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter
import woowacourse.movie.ui.ticket.MovieTicketActivity

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationFragmentContext: Context,
) : ReservationContract.Presenter {
    private val reservationTickets: List<MovieTicketModel> by lazy { ReservationTicketMachine.tickets }
    override fun initAdapter() {
        view.reservationAdapter =
            ReservationAdapter(reservationTickets, ::setEventOnReservationItems)
    }

    override fun isEmptyMovieReservation() {
        view.setTextOnEmptyState(reservationTickets.isEmpty())
    }

    private fun setEventOnReservationItems(movieTicketModel: MovieTicketModel) {
        reservationFragmentContext.startActivity(movieTicketModel.moveToMovieTicketActivity())
    }

    private fun MovieTicketModel.moveToMovieTicketActivity(): Intent =
        Intent(reservationFragmentContext, MovieTicketActivity::class.java).apply {
            putExtra(KEY_TICKET, this@moveToMovieTicketActivity)
        }

    companion object {
        private const val KEY_TICKET = "TICKET"
    }
}
