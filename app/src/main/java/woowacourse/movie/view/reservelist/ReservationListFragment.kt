package woowacourse.movie.view.reservelist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.Provider
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.base.BaseFragment
import woowacourse.movie.view.reservation.result.ReservationResultActivity

class ReservationListFragment :
    BaseFragment<FragmentReservationListBinding>(R.layout.fragment_reservation_list),
    ReservationListContract.View {
    private val presenter: ReservationListPresenter by lazy {
        Provider.reservationListPresenter(this)
    }

    override fun showReservationList(data: List<Ticket>) {
        binding.lvReservationList.adapter = ReservationListAdapter(data, ::navigateToReservationResult)
        binding.lvReservationList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadData()
    }

    private fun navigateToReservationResult(ticket: Ticket) {
        val intent = ReservationResultActivity.newIntent(requireContext(), ticket)
        startActivity(intent)
    }
}
