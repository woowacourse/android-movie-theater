package woowacourse.movie.feature.reservation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepositoryImpl
import woowacourse.movie.feature.reservation.confirm.TicketsConfirmActivity
import woowacourse.movie.model.TicketsState

class ReservationListFragment :
    Fragment(R.layout.fragment_reservation_list),
    ReservationListContract.View {

    private lateinit var reservationRecyclerView: RecyclerView

    private val adapter: TicketListAdapter by lazy {
        TicketListAdapter(itemClickEvent = presenter::showTicketsConfirm)
    }
    private val presenter: ReservationListContract.Presenter by lazy {
        ReservationListPresenter(this, TicketsRepositoryImpl())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadListItems()
    }

    override fun setTicketList(items: List<TicketsState>) {
        adapter.setItems(items)
    }

    override fun showTicketsConfirm(tickets: TicketsState) {
        TicketsConfirmActivity.startActivity(requireActivity(), tickets)
    }

    private fun init(view: View) {
        reservationRecyclerView = view.findViewById(R.id.reservation_rv)
        reservationRecyclerView.adapter = adapter
    }
}
