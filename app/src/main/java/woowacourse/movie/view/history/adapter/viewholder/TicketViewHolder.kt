package woowacourse.movie.view.history.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTicketBinding
import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.view.history.adapter.OnClickTicketItem

class TicketViewHolder(
    private val binding: ItemTicketBinding,
    private val onClickTicketItem: OnClickTicketItem,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.ticketCatalog = this
    }

    fun bind(item: ReservationTicket) {
        binding.reservationTicket = item
    }

    fun onClickTicket(item: ReservationTicket) = onClickTicketItem(item)
}
