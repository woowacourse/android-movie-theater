package woowacourse.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationHistoryItemBinding
import woowacourse.movie.ticket.model.DbTicket

class ReservationHistoryAdapter(
    private var tickets: List<DbTicket>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ReservationHistoryViewHolder(val binding: ReservationHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dbTicket: DbTicket) {
            binding.dbTicket = dbTicket
        }
    }

    fun updateItems(items: List<DbTicket>) {
        this.tickets = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ReservationHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
