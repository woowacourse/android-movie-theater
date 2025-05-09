package woowacourse.movie.ui.view.history.adapter

import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.screening.adapter.BaseViewHolder

class ReservationViewHolder(private val binding: ItemReservationBinding) :
    BaseViewHolder<Ticket>(itemView = binding.root) {
    override fun bind(item: Ticket) {
        binding.ticket = item
    }
}
