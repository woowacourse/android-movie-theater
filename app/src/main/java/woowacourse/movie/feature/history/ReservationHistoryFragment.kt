package woowacourse.movie.feature.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRoomRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.feature.history.adapter.ReservationHistoryAdapter
import woowacourse.movie.feature.history.adapter.ReservationHistoryDividerDecoration
import woowacourse.movie.feature.result.MovieResultActivity
import woowacourse.movie.util.BaseFragment

class ReservationHistoryFragment :
    BaseFragment<ReservationHistoryContract.Presenter>(),
    ReservationHistoryContract.View {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter
    private val ticketRepository by lazy { TicketRoomRepository(TicketDatabase.instance(requireActivity().application).ticketDao()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(inflater)

        reservationHistoryAdapter =
            ReservationHistoryAdapter {
                val intent = MovieResultActivity.newIntent(requireContext(), it.id)
                startActivity(intent)
            }

        initializeView()
        return binding.root
    }

    override fun initializePresenter() = ReservationHistoryPresenter(this)

    private fun initializeView() {
        binding.reservationHistoryRecyclerView.adapter = reservationHistoryAdapter
        binding.reservationHistoryRecyclerView.addItemDecoration(ReservationHistoryDividerDecoration())
    }

    override fun onResume() {
        super.onResume()
        presenter.loadTickets(ticketRepository)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displayTickets(tickets: List<Ticket>) {
        reservationHistoryAdapter.submitList(tickets)
    }
}
