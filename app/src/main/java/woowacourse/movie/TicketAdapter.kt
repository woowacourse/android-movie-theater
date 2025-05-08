package woowacourse.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemTicketBinding
import woowacourse.movie.domain.ticket.Ticket

class TicketAdapter : ListAdapter<Ticket, TicketViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TicketViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTicketBinding.inflate(layoutInflater, parent, false)
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), position != itemCount - 1)
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<Ticket>() {
                override fun areItemsTheSame(
                    oldItem: Ticket,
                    newItem: Ticket,
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: Ticket,
                    newItem: Ticket,
                ): Boolean = oldItem.title == newItem.title
            }
    }
}
