package woowacourse.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationHistoryItemBinding
import woowacourse.movie.ticket.model.TicketEntity

class ReservationHistoryAdapter(
    private val tickets: List<TicketEntity>,
    private val clickListener: ReservationOnItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ReservationHistoryViewHolder(val binding: ReservationHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ticketEntity: TicketEntity) {
            binding.dbTicket = ticketEntity
            binding.reservationOnItemClickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ReservationHistoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReservationHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val reservationHistoryHolder = holder as ReservationHistoryViewHolder
        reservationHistoryHolder.bind(tickets[position])
    }

    override fun getItemCount(): Int {
        return tickets.size
    }
}
