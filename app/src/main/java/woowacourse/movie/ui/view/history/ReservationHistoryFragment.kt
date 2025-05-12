package woowacourse.movie.ui.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.local.adapter.TicketAdapter
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.history.adapter.ReservationAdapter
import woowacourse.movie.ui.view.ticket.TicketActivity

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var presenter: ReservationHistoryContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val database = getMovieDatabase(requireContext())
        presenter = ReservationHistoryPresenter(this, TicketAdapter(database.ticketDao()))
        reservationAdapter =
            ReservationAdapter { ticket: Ticket ->
                ticket.run {
                    val intent =
                        TicketActivity.newIntent(
                            requireContext(),
                            title,
                            count,
                            showtime,
                            cinemaName,
                            seats,
                            purchaseType,
                        )
                    startActivity(intent)
                }
            }
        binding.recyclerViewReservations.adapter = reservationAdapter
        presenter.presentScreen()
    }

    override fun updateScreen(tickets: List<Ticket>) {
        requireActivity().runOnUiThread {
            reservationAdapter.submitList(tickets)
        }
    }
}
