package woowacourse.movie.feature.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.feature.history.adapter.ReservationHistoryAdapter
import woowacourse.movie.feature.result.MovieResultActivity
import woowacourse.movie.util.BaseFragment

class ReservationHistoryFragment :
    BaseFragment<ReservationHistoryContract.Presenter>(),
    ReservationHistoryContract.View {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(inflater)

        initializeView()
        return binding.root
    }

    override fun initializePresenter() = ReservationHistoryPresenter(this)

    private fun initializeView() {
        reservationHistoryAdapter =
            ReservationHistoryAdapter {
                val intent = MovieResultActivity.newIntent(requireContext(), it.id)
                startActivity(intent)
            }
        binding.reservationHistoryRecyclerView.adapter = reservationHistoryAdapter
        presenter.loadTickets((requireActivity().application as MovieTheaterApplication).ticketRepository)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displayTickets(tickets: List<Ticket>) {
        reservationHistoryAdapter.submitList(tickets)
    }
}
