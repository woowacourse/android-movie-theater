package woowacourse.movie.presentation.history

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.model.ReservationHistoryUiModel
import woowacourse.movie.presentation.common.model.TicketUiModel
import woowacourse.movie.presentation.history.adapter.ReservationHistoryEventListener
import woowacourse.movie.presentation.history.adapter.ReservationsAdapter
import woowacourse.movie.presentation.home.reservation.result.ReservationResultActivity

class ReservationHistoryFragment :
    BaseFragment<FragmentReservationHistoryBinding>(R.layout.fragment_reservation_history),
    ReservationHistoryContract.View,
    ReservationHistoryEventListener {
    private val presenter: ReservationHistoryPresenter by lazy { ReservationHistoryPresenter(this) }
    private val adapter by lazy { ReservationsAdapter(this) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setHistoryAdapter()
        fetchReservations()
    }

    override fun onResume() {
        super.onResume()
        fetchReservations()
    }

    private fun fetchReservations() {
        presenter.fetchData()
    }

    override fun showReservationHistory(histories: List<ReservationHistoryUiModel>) {
        requireActivity().runOnUiThread {
            adapter.submitList(histories)
        }
    }

    override fun onHistoryClick(ticket: TicketUiModel) {
        val intent = ReservationResultActivity.newIntent(requireContext(), ticket)
        startActivity(intent)
    }

    private fun setHistoryAdapter() {
        val decoration = DividerItemDecoration(requireContext(), VERTICAL)
        binding.rvReservationHistory.addItemDecoration(decoration)
        binding.rvReservationHistory.adapter = adapter
    }
}
