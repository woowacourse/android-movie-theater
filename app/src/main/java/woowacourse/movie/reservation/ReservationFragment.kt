package woowacourse.movie.reservation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room
import woowacourse.movie.R
import woowacourse.movie.base.BindingFragment
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.database.Ticket
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.purchaseConfirmation.PurchaseConfirmationActivity

class ReservationFragment :
    BindingFragment<FragmentReservationBinding>(R.layout.fragment_reservation),
    ReservationContract.View {
    private lateinit var presenter: ReservationPresenter
    private lateinit var reservationAdapter: ReservationAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val database =
            Room.databaseBuilder(requireContext(), AppDatabase::class.java, "ticket").build()
        presenter = ReservationPresenter(this, database)
        reservationAdapter =
            ReservationAdapter {
                presenter.navigateDetailView(it)
            }
        binding.reservationRecyclerView.adapter = reservationAdapter
        presenter.loadData()
    }

    override fun showReservations(reservations: List<Ticket>) {
        view?.post {
            reservationAdapter.updateReservations(reservations)
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun navigateToTicketDetail(ticket: Ticket) {
        val intent = Intent(requireContext(), PurchaseConfirmationActivity::class.java)
        intent.putExtra(EXTRA_TICKET_PRICE, ticket.ticketPrice.toString())
        intent.putExtra(EXTRA_SEAT_NUMBER, ticket.seatNumbers.split(",").toTypedArray())
        intent.putExtra(EXTRA_CINEMA_NAME, ticket.cinemaName)
        intent.putExtra(EXTRA_MOVIE_TITLE, ticket.movieTitle)
        intent.putExtra(EXTRA_RUNNING_TIME, ticket.runningTime)
        intent.putExtra(EXTRA_TIME_DATE, ticket.date)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_TICKET_PRICE = "ticketPrice"
        const val EXTRA_SEAT_NUMBER = "seatNumber"
        const val EXTRA_CINEMA_NAME = "cinemaName"
        const val EXTRA_MOVIE_TITLE = "title"
        const val EXTRA_RUNNING_TIME = "runningTime"
        const val EXTRA_TIME_DATE = "timeDate"
    }
}
