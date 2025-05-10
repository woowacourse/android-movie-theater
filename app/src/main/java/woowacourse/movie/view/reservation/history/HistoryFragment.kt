package woowacourse.movie.view.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.TicketInfoDatabase
import woowacourse.movie.data.TicketRepository
import woowacourse.movie.databinding.FragmentHistoryBinding
import kotlin.concurrent.thread

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyAdapter = HistoryAdapter()

    private var recyclerView: RecyclerView? = null

    private val repository: TicketRepository by lazy {
        val dao = TicketInfoDatabase.getDatabase(requireContext()).ticketInfoDao()
        TicketRepository(dao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerView

        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = historyAdapter

        thread {
            val tickets = repository.loadTickets()
            requireActivity().runOnUiThread {
                (binding.recyclerView.adapter as HistoryAdapter).submitList(tickets)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
