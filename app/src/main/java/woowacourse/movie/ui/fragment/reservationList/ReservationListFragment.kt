package woowacourse.movie.ui.fragment.reservationList

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.confirm.ReservationConfirmActivity
import woowacourse.movie.ui.main.adapter.ReservationListAdapter
import woowacourse.movie.ui.main.itemModel.TicketsItemModel

class ReservationListFragment : Fragment(R.layout.fragment_reservation_list) {

    private var reservationRecyclerView: RecyclerView? = null
    private var adapter: ReservationListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReservationListAdapter(
            TicketsRepository.allTickets().map(::TicketsItemModel)
        ) { ticketsItemModel ->
            navigateReservationConfirm(ticketsItemModel.ticketsState)
        }

        reservationRecyclerView = view.findViewById(R.id.reservation_rv)
        reservationRecyclerView?.adapter = adapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        adapter?.setItemChanged(
            TicketsRepository.allTickets().map(::TicketsItemModel)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reservationRecyclerView = null
        adapter = null
    }

    private fun navigateReservationConfirm(ticketsState: TicketsState) {
        ReservationConfirmActivity.startActivity(activity as Context, ticketsState)
    }
}
