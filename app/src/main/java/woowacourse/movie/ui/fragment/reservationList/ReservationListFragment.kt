package woowacourse.movie.ui.fragment.reservationList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.confirm.ReservationConfirmActivity
import woowacourse.movie.ui.main.adapter.ReservationListAdapter
import woowacourse.movie.ui.main.itemModel.TicketsItemModel

class ReservationListFragment : Fragment() {

    private lateinit var reservationRecyclerView: RecyclerView
    private lateinit var adapter: ReservationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservationRecyclerView = view.findViewById(R.id.reservation_rv)
        adapter = ReservationListAdapter(
            TicketsRepository.allTickets().map {
                it.convertToItemModel { position ->
                    navigateReservationConfirm((adapter.reservations[position] as TicketsItemModel).ticketsState)
                }
            }
        )
        reservationRecyclerView.adapter = adapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        adapter.setItemChanged(
            TicketsRepository.allTickets().map {
                it.convertToItemModel { position ->
                    navigateReservationConfirm((adapter.reservations[position] as TicketsItemModel).ticketsState)
                }
            }
        )
    }

    private fun navigateReservationConfirm(ticketsState: TicketsState) {
        startActivity(ReservationConfirmActivity.getIntent(activity as Context, ticketsState))
    }
}
