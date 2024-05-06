package woowacourse.movie.view.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTicketBinding
import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.view.history.adapter.viewholder.TicketViewHolder

typealias OnClickTicketItem = (ReservationTicket) -> Unit

class ReservationTicketAdapter(
    private val onClickTicketItem: OnClickTicketItem,
) : RecyclerView.Adapter<TicketViewHolder>() {
    private var reservationTickets: ArrayList<ReservationTicket> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(view, onClickTicketItem)
    }

    override fun getItemCount(): Int {
        return reservationTickets.size
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val item = reservationTickets[position]
        holder.bind(item)
    }

    fun updateTickets(reservationTickets: List<ReservationTicket>) {
        val startInsertPosition = this.reservationTickets.size
        this.reservationTickets.addAll(reservationTickets)
        notifyItemRangeInserted(startInsertPosition, reservationTickets.size)
    }
}
