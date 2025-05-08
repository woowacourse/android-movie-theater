package woowacourse.movie.presentation.ticket.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.common.adapter.ItemClickListener
import woowacourse.movie.databinding.ItemTicketBinding
import woowacourse.movie.domain.model.Ticket

class TicketViewHolder(
    private val binding: ItemTicketBinding,
    private val onTicketClicked: (Ticket) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: Ticket

    init {
        binding.handler = ItemClickListener<Ticket> { onTicketClicked(currentItem) }
    }

    fun bind(item: Ticket) {
        currentItem = item
        binding.ticket = item
    }
}
