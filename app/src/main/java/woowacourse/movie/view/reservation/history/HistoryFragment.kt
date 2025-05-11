package woowacourse.movie.view.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.view.reservation.TicketUi
import woowacourse.movie.view.reservation.result.ReservationCompleteActivity

class HistoryFragment : Fragment(), HistoryContract.View {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val presenter by lazy { HistoryPresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        showDivider()
        presenter.loadTickets()
    }

    private fun showDivider() {
        val dividerItemDecoration =
            DividerItemDecoration(
                binding.rvReservationHistory.context,
                LinearLayoutManager.VERTICAL,
            )
        binding.rvReservationHistory.addItemDecoration(dividerItemDecoration)
    }

    override fun showMoviesScreen(ticketUis: List<TicketUi>) {
        binding.tickets = ticketUis
        binding.onItemClick =
            object : OnReservationEventListener {
                override fun onClickReservation(index: Int) {
                    presenter.onTicketSelected(index)
                }
            }
    }

    override fun handleReservationComplete(ticketUi: TicketUi) {
        val intent =
            ReservationCompleteActivity.newIntent(this.requireContext(), ticketUi)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
