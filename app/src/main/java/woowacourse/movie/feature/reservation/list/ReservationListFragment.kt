package woowacourse.movie.feature.reservation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.feature.common.adapter.CommonListAdapter
import woowacourse.movie.feature.reservation.confirm.TicketsConfirmActivity
import woowacourse.movie.model.TicketsState

class ReservationListFragment : Fragment(R.layout.fragment_reservation_list) {

    private lateinit var reservationRecyclerView: RecyclerView
    private lateinit var adapter: CommonListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        setItems()
    }

    private fun init(view: View) {
        reservationRecyclerView = view.findViewById(R.id.reservation_rv)
        adapter = CommonListAdapter()
        setItems()
        reservationRecyclerView.adapter = adapter
    }

    private fun setItems() {
        adapter.setItems(
            TicketsRepository.allTickets().map { ticketState ->
                ticketState.toItemModel { navigateReservationConfirm(ticketState) }
            }
        )
    }

    private fun navigateReservationConfirm(ticketsState: TicketsState) {
        TicketsConfirmActivity.startActivity(requireActivity(), ticketsState)
    }
}
