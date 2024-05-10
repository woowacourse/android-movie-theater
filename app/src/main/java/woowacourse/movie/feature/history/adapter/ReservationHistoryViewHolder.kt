package woowacourse.movie.feature.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.db.ticket.Ticket

typealias OnClickItem = () -> Unit

class ReservationHistoryViewHolder(
    private val binding: ItemReservationHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        ticket: Ticket,
        onClickItem: OnClickItem,
    ) {
        binding.ticket = ticket
        itemView.setOnClickListener { onClickItem() }
    }
}
