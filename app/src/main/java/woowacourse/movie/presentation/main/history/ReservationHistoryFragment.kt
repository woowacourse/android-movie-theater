package woowacourse.movie.presentation.main.history

import android.content.Intent
import android.view.View
import woowacourse.movie.R
import woowacourse.movie.data.repository.MovieTicketRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.domain.model.movieticket.MovieTicket
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.main.history.adapter.ReservationHistoryAdapter
import woowacourse.movie.presentation.seats.SeatsActivity.Companion.EXTRA_MOVIE_TICKET_ID
import woowacourse.movie.presentation.ticket.MovieTicketActivity

class ReservationHistoryFragment :
    BaseFragment<FragmentReservationHistoryBinding>(R.layout.fragment_reservation_history),
    ReservationHistoryContract.View {
    private lateinit var presenter: ReservationHistoryContract.Presenter
    private lateinit var adapter: ReservationHistoryAdapter

    override fun onViewCreatedSetup() {
        val movieTicketRepository = MovieTicketRepositoryImpl(requireContext())
        presenter = ReservationHistoryPresenter(this, movieTicketRepository)
        presenter.loadReservationHistory()
    }

    override fun showReservationHistory(movieTickets: List<MovieTicket>) {
        if (movieTickets.isEmpty()) {
            binding?.reservationHistoryRecyclerView?.visibility = View.INVISIBLE
            binding?.tvEmptyReservation?.visibility = View.VISIBLE
        } else {
            binding?.tvEmptyReservation?.visibility = View.INVISIBLE
            adapter = ReservationHistoryAdapter(
                movieTickets,
                clickListener = presenter,
            )
            binding?.reservationHistoryRecyclerView?.adapter = adapter
            binding?.reservationHistoryRecyclerView?.visibility = View.VISIBLE
        }
    }

    override fun navigateToReservationActivity(movieTicketId: Long) {
        val intent = Intent(activity, MovieTicketActivity::class.java).apply {
            putExtra(EXTRA_MOVIE_TICKET_ID, movieTicketId)
        }
        startActivity(intent)
    }
}
