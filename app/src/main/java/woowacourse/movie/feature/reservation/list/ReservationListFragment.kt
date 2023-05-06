package woowacourse.movie.feature.reservation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.feature.common.adapter.CommonListAdapter
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.reservation.confirm.TicketsConfirmActivity
import woowacourse.movie.model.TicketsState

class ReservationListFragment :
    Fragment(R.layout.fragment_reservation_list), ReservationListContract.View {

    private lateinit var reservationRecyclerView: RecyclerView
    private lateinit var adapter: CommonListAdapter
    private lateinit var presenter: ReservationListContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    override fun onResume() {
        super.onResume()
        presenter.setListItems()
    }

    override fun setItems(items: List<ItemModel>) { adapter.setItems(items) }

    override fun navigateTicketsConfirm(tickets: TicketsState) {
        TicketsConfirmActivity.startActivity(requireActivity(), tickets)
    }

    private fun init(view: View) {
        reservationRecyclerView = view.findViewById(R.id.reservation_rv)
        adapter = CommonListAdapter()
        presenter = ReservationListPresenter(this)
        reservationRecyclerView.adapter = adapter
    }
}
