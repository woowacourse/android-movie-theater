package woowacourse.movie.ui.main.reservationList

import android.content.Context
import android.os.Bundle
import android.view.View
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.dbHelper.TicketsDbHelper
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.BaseFragment
import woowacourse.movie.ui.adapter.ReservationListAdapter
import woowacourse.movie.ui.confirm.ReservationConfirmActivity
import woowacourse.movie.ui.itemModel.TicketsItemModel

class ReservationListFragment : BaseFragment(), ReservationListContract.View {
    override lateinit var presenter: ReservationListContract.Presenter
    override lateinit var binding: FragmentReservationListBinding

    private lateinit var adapter: ReservationListAdapter

    override fun initBinding() {
        binding = FragmentReservationListBinding.inflate(layoutInflater)
    }

    override fun initPresenter() {
        presenter = ReservationListPresenter(this, TicketsDbHelper(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getReservationList()
    }

    override fun setAdapter(tickets: List<TicketsState>) {
        adapter = ReservationListAdapter(
            tickets.map(::TicketsItemModel)
        ) { ticketsItemModel ->
            navigateReservationConfirm(ticketsItemModel.ticketsState)
        }
        binding.reservationList.adapter = adapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) { presenter.getReservationList() }
    }

    private fun navigateReservationConfirm(ticketsState: TicketsState) {
        ReservationConfirmActivity.startActivity(activity as Context, ticketsState)
    }
}
