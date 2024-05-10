package woowacourse.movie.presentation.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.presentation.uimodel.TicketUiModel
import woowacourse.movie.repository.db.ReservationTicketDatabase

class ReservationListFragment : Fragment(), ReservationListContract.View {
    private lateinit var adapter: ReservationListAdapter
    private lateinit var binding: FragmentReservationListBinding
    private lateinit var presenter: ReservationListPresenterImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentReservationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticketDao = ReservationTicketDatabase.getInstance(requireContext()).reservationDao()
        presenter = ReservationListPresenterImpl(ticketDao = ticketDao, view = this)
        adapter = ReservationListAdapter(emptyList())
        binding.reservationRecyclerView.adapter = adapter

        presenter.loadReservationTickets()
    }

    override fun showReservationTickets(tickets: List<TicketUiModel>) {
        activity?.runOnUiThread {
            adapter.ticketList = tickets
            adapter.notifyDataSetChanged()
        }
    }
}
