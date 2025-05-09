package woowacourse.movie.view.reservation.history

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationHistoryItemBinding
import woowacourse.movie.domain.Ticket

class TicketViewHolder(private val binding: ReservationHistoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(ticket: Ticket) {
        binding.ticket = ticket
    }
}
