package woowacourse.movie.view.reservation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ReservationHistoryItemBinding
import woowacourse.movie.domain.Ticket

class HistoryAdapter(
    private val onItemClick: (Ticket) -> Unit,
) : ListAdapter<Ticket, TicketViewHolder>(HistoryListItemDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TicketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReservationHistoryItemBinding.inflate(inflater, parent, false)

        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), onItemClick)
    }

    companion object {
        private val HistoryListItemDiffCallback =
            object : DiffUtil.ItemCallback<Ticket>() {
                override fun areItemsTheSame(
                    oldItem: Ticket,
                    newItem: Ticket,
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: Ticket,
                    newItem: Ticket,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
