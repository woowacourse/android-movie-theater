package woowacourse.movie.view.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.FragmentHistoryBinding
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.domain.movieseat.Seat
import woowacourse.movie.domain.movieseat.Seats
import java.time.LocalDateTime

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyAdapter = HistoryAdapter()

    private var recyclerView: RecyclerView? = null

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

        val dummyTickets =
            listOf(
                Ticket(
                    "Movie A",
                    LocalDateTime.now(),
                    2,
                    "잠실 극장",
                    Seats(setOf(Seat(Position(1, 1)), Seat(Position(2, 1)))),
                ),
                Ticket(
                    "Movie B",
                    LocalDateTime.now(),
                    1,
                    "선릉 극장",
                    Seats(setOf(Seat(Position(3, 1)))),
                ),
            )

        historyAdapter.submitList(dummyTickets)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
