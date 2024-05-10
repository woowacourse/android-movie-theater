package woowacourse.movie.feature.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.db.ticket.Ticket
import woowacourse.movie.db.ticket.TicketDao
import woowacourse.movie.db.ticket.TicketDatabase
import woowacourse.movie.feature.finished.ReservationFinishedActivity
import woowacourse.movie.feature.history.adapter.ReservationHistoryAdapter

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: ReservationHistoryPresenter
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_history, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initReservationHistoryRecyclerView()
        presenter.loadTicket()
    }

    override fun showReservationHistory(tickets: List<Ticket>) {
        reservationHistoryAdapter.updateData(tickets)
    }

    override fun navigateToReservationInformation(ticketId: Long?) {
        val intent = Intent(context, ReservationFinishedActivity::class.java).putExtra(TICKET_ID, ticketId)
        startActivity(intent)
    }

    private fun initPresenter() {
        val ticketDao: TicketDao = TicketDatabase.initialize(requireContext()).ticketDao()
        presenter = ReservationHistoryPresenter(this, ticketDao)
    }

    private fun initReservationHistoryRecyclerView() {
        reservationHistoryAdapter =
            ReservationHistoryAdapter { ticketId ->
                presenter.deliverTicketId(ticketId)
            }
        binding.rvReservationHistory.adapter = reservationHistoryAdapter
        val dividerItemDecoration = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvReservationHistory.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TICKET_ID = "ticketId"
    }
}
