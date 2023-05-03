package woowacourse.movie.feature.reservation.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.feature.common.itemModel.TicketsItemModel
import woowacourse.movie.feature.reservation.MovieDetailActivity
import woowacourse.movie.feature.reservation.confirm.ReservationConfirmActivity
import woowacourse.movie.model.TicketsState

class ReservationListFragment : Fragment(R.layout.fragment_reservation_list) {

    private lateinit var reservationRecyclerView: RecyclerView
    private lateinit var adapter: ReservationListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservationRecyclerView = view.findViewById(R.id.reservation_rv)
        adapter = ReservationListAdapter(
            TicketsRepository.allTickets().map {
                it.toItemModel { position ->
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
                it.toItemModel { position ->
                    navigateReservationConfirm((adapter.reservations[position] as TicketsItemModel).ticketsState)
                }
            }
        )
    }

    private fun navigateReservationConfirm(ticketsState: TicketsState) {
        val intent = Intent(activity, ReservationConfirmActivity::class.java)
        intent.putExtra(MovieDetailActivity.KEY_TICKETS, ticketsState)
        startActivity(intent)
    }
}
