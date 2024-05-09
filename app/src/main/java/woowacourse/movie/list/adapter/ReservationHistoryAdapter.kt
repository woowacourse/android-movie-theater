package woowacourse.movie.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.common.CommonDataSource
import woowacourse.movie.databinding.ReservationHistoryItemBinding
import woowacourse.movie.ticket.model.DbTicket

class ReservationHistoryAdapter(
    private var tickets: List<DbTicket> = CommonDataSource.dbTickets
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
        Log.d("alsong", "A-onCreate}")
        val binding = ReservationHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("alsong", "A-onBind ${tickets[position]}")
        val reservationHistoryHolder = holder as ReservationHistoryViewHolder
        reservationHistoryHolder.bind(tickets[position])
        Log.d("alsong", "A-onBind ${tickets[position]}")
    }

    override fun getItemCount(): Int {
        return tickets.size
    }
}
