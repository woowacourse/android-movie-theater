package woowacourse.movie.view.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.TicketRepositoryImpl
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.view.reservation.Ticket
import woowacourse.movie.view.reservation.result.ReservationCompleteActivity
import kotlin.concurrent.thread

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val ticketRepositoryImpl = TicketRepositoryImpl()

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
        super.onViewCreated(view, savedInstanceState)
        showMoviesScreen()
    }

    private fun showMoviesScreen() {
        thread {
            val tickets = ticketRepositoryImpl.getAll()
            binding.tickets = tickets
            binding.onItemClick =
                object : OnReservationEventListener {
                    override fun onClickReservation(index: Int) {
                        handleReservationComplete(tickets[index])
                    }
                }
        }
    }

    private fun handleReservationComplete(ticket: Ticket) {
        val intent =
            ReservationCompleteActivity.newIntent(this.requireContext(), ticket)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
