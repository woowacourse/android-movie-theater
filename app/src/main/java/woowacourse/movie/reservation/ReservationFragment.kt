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
        val intent = Intent(requireContext(), PurchaseConfirmationActivity::class.java).apply {
            putExtra(EXTRA_TICKET_ID, ticket.id)
        }
        startActivity(intent)
    }

    companion object {
        const val EXTRA_TICKET_ID = "ticketId"
    }
}
