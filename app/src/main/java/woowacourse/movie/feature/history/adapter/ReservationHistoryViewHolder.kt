package woowacourse.movie.feature.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.model.ticket.Ticket

typealias OnSelectedReservation = (ticketId: Long) -> Unit

class ReservationHistoryViewHolder(
    private val binding: ItemReservationHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        ticket: Ticket,
        onSelectedReservation: OnSelectedReservation,
    ) {
        binding.ticket = ticket
        itemView.setOnClickListener { onSelectedReservation(ticket.uid) }
    }
}
