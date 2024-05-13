package woowacourse.movie.presentation.view.reservation.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationItemBinding
import woowacourse.movie.presentation.uimodel.TicketUiModel
import woowacourse.movie.presentation.view.reservation.history.ReservationListContract

class ReservationListAdapter(
    var ticketList: List<TicketUiModel>,
    private val listener: ReservationListContract.ItemListener,
) : RecyclerView.Adapter<ReservationViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationViewHolder {
        val reservationViewHolder =
            ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(reservationViewHolder)
    }

    override fun getItemCount(): Int = ticketList.size

    override fun onBindViewHolder(
        holder: ReservationViewHolder,
        position: Int,
    ) {
        holder.bind(ticketList[position], listener)
    }
}
