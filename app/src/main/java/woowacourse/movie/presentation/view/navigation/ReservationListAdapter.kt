package woowacourse.movie.presentation.view.navigation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationItemBinding
import woowacourse.movie.presentation.uimodel.TicketUiModel

class ReservationListAdapter(
    var ticketList: List<TicketUiModel>,
) : RecyclerView.Adapter<ReservationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        Log.d("ReservationListFragment", "create viewHolder")
        val reservationViewHolder =
            ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(reservationViewHolder)
    }

    override fun getItemCount(): Int = ticketList.size

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        Log.d("ReservationListFragment", "bind viewHolder")
        holder.bind(ticketList[position])
    }
}
