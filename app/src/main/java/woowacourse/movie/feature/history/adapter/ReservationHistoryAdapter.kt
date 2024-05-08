package woowacourse.movie.feature.history.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.model.ticket.Ticket

class ReservationHistoryAdapter : RecyclerView.Adapter<ReservationHistoryViewHolder>() {
    private var tickets = listOf<Ticket>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemReservationHistoryBinding.inflate(inflater, parent, false)
        return ReservationHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(
        holder: ReservationHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(tickets[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTickets: List<Ticket>) {
        tickets = newTickets
        notifyDataSetChanged()
    }
}
