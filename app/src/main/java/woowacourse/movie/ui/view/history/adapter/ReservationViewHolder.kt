package woowacourse.movie.ui.view.history.adapter

import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.ticket.TicketHistory
import woowacourse.movie.ui.view.screening.adapter.BaseViewHolder

class ReservationViewHolder(
    private val binding: ItemReservationBinding,
    private val onClick: (ticketHistory: TicketHistory) -> Unit,
) :
    BaseViewHolder<TicketHistory>(itemView = binding.root) {
    override fun bind(item: TicketHistory) {
        binding.ticket = item
        binding.root.setOnClickListener {
            onClick(item)
        }
    }
}
