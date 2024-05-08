package woowacourse.movie.presentation.ui.main.history

import woowacourse.movie.R
import woowacourse.movie.data.repository.local.ReservationRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.presentation.base.BaseMvpBindingFragment
import woowacourse.movie.presentation.ui.main.history.adapter.ReservationHistoryAdapter
import woowacourse.movie.presentation.ui.reservation.ReservationActivity

class ReservationHistoryFragment :
    BaseMvpBindingFragment<FragmentReservationHistoryBinding>(),
    ReservationHistoryContract.View {
    override val layoutResourceId: Int
        get() = R.layout.fragment_reservation_history

    val presenter: ReservationHistoryPresenter by lazy {
        ReservationHistoryPresenter(
            this,
            ReservationRepositoryImpl(requireContext().applicationContext),
        )
    }
    private val adapter: ReservationHistoryAdapter by lazy { ReservationHistoryAdapter(presenter) }

    override fun initStartView() {
        presenter.loadReservations()
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvReservation.adapter = adapter
    }

    override fun showReservations(reservations: List<Reservation>) {
        requireActivity().runOnUiThread {
            adapter.updateReservations(reservations)
        }
    }

    override fun navigateToReservation(reservationId: Long) {
        ReservationActivity.startActivity(requireActivity(), reservationId)
    }

    companion object {
        const val TAG = "RESERVATION_HISTORY_FRAGMENT"
    }
}
