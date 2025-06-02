package woowacourse.movie.view.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.TicketInfoDatabase
import woowacourse.movie.data.TicketRepository
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.domain.Ticket
import woowacourse.movie.view.reservation.result.ReservationCompleteActivity

class HistoryFragment :
    Fragment(),
    HistoryContract.View {
    private var _binding: FragmentHistoryBinding? = null
    val binding get() = _binding!!

    private lateinit var presenter: HistoryContract.Presenter

    private val historyAdapter =
        HistoryAdapter { ticket ->
            navigateToReservationComplete(ticket)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        initPresenter()

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = historyAdapter

        presenter.loadTickets()
    }

    override fun showTickets(tickets: List<Ticket>) {
        historyAdapter.submitList(tickets)
    }

    override fun navigateToReservationComplete(ticket: Ticket) {
        val intent = ReservationCompleteActivity.newIntent(requireContext(), ticket)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initPresenter() {
        val dao = TicketInfoDatabase.getDatabase(requireContext()).ticketInfoDao()
        val repository = TicketRepository(dao)
        presenter = HistoryPresenter(this, repository)
    }
}
