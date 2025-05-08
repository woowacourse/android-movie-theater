package woowacourse.movie.view.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.TicketAdapter
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.domain.ticket.Ticket
import java.time.LocalDateTime

class ReservationHistoryFragment : Fragment() {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding get() = requireNotNull(_binding) { "_binding is null" }
    private var adapter: TicketAdapter? = null
    private val mockData: List<Ticket> =
        listOf(
            Ticket("영화 제목", 3, LocalDateTime.now()),
            Ticket("영화 제목", 3, LocalDateTime.now()),
            Ticket("영화 제목", 3, LocalDateTime.now()),
            Ticket("영화 제목", 3, LocalDateTime.now()),
            Ticket("영화 제목", 3, LocalDateTime.now()),
            Ticket("영화 제목", 3, LocalDateTime.now()),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TicketAdapter()
        binding.adapter = adapter
        adapter?.submitList(mockData)
    }

    override fun onDestroy() {
        _binding = null
        adapter = null
        super.onDestroy()
    }
}
