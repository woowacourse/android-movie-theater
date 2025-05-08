package woowacourse.movie.view.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.ReservationHistoryPresenter
import woowacourse.movie.TicketAdapter
import woowacourse.movie.contract.reservation.ReservationHistoryContract
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.domain.ticket.Ticket
import java.time.LocalDateTime

class ReservationHistoryFragment :
    Fragment(),
    ReservationHistoryContract.View {
    private val presenter: ReservationHistoryContract.Presenter = ReservationHistoryPresenter(this)
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding get() = requireNotNull(_binding) { "_binding is null" }
    private var adapter: TicketAdapter? = null
    private val mockData: List<Ticket> =
        listOf(
            Ticket("해리 포터와 마법사의 돌", LocalDateTime.of(2024, 3, 2, 17, 0), emptySet(), "선릉 극장"),
            Ticket("해리 포터와 마법사의 돌", LocalDateTime.of(2024, 3, 3, 13, 0), emptySet(), "선릉 극장"),
            Ticket("해리 포터와 비밀의 방", LocalDateTime.of(2024, 4, 2, 16, 0), emptySet(), "잠실 극장"),
            Ticket("해리 포터와 아즈카반의 죄수", LocalDateTime.of(2024, 5, 2, 17, 0), emptySet(), "강남 극장"),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(layoutInflater, container, false)
        adapter = TicketAdapter()
        binding.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.fetchReservationHistories()
    }

    override fun onDestroy() {
        _binding = null
        adapter = null
        super.onDestroy()
    }

    override fun updateReservationHistories() {
        adapter?.submitList(mockData)
    }
}
