package woowacourse.movie.view.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.contract.reservation.ReservationHistoryContract
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.domain.ticket.Reservation
import woowacourse.movie.presenter.reservation.ReservationHistoryPresenter
import woowacourse.movie.view.ticket.ReservationDetailActivity

class ReservationHistoryFragment :
    Fragment(),
    ReservationHistoryContract.View {
    private val presenter: ReservationHistoryContract.Presenter = ReservationHistoryPresenter(this)

    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding get() = requireNotNull(_binding) { "_binding is null" }

    private var adapter: ReservationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(layoutInflater, container, false)
        adapter = ReservationAdapter(presenter::selectReservation)
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

    override fun onDestroyView() {
        _binding = null
        adapter = null
        super.onDestroyView()
    }

    override fun updateReservationHistories(reservations: List<Reservation>) {
        requireActivity().runOnUiThread {
            adapter?.submitList(reservations)
        }
    }

    override fun showTicket(reservation: Reservation) {
        val intent = ReservationDetailActivity.newIntent(requireContext(), reservation)
        startActivity(intent)
    }
}
