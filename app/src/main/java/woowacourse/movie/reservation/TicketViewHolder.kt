package woowacourse.movie.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TicketListItemBinding
import woowacourse.movie.ui.model.TicketUiModel

class TicketViewHolder(
    parent: ViewGroup,
    onTicketClick: TicketClickListener,
) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.ticket_list_item, parent, false),
    ) {
    private val binding = TicketListItemBinding.bind(itemView)

    init {
        binding.clickListener = onTicketClick
    }

    fun bind(ticket: TicketUiModel) {
        binding.ticket = ticket
    }
}
