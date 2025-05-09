package woowacourse.movie.view.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.history.viewholder.HistoryViewHolder

class HistoryAdapter(
    private val itemsList: List<Ticket>,
) : ListAdapter<Ticket, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<Ticket>() {
            override fun areItemsTheSame(
                oldItem: Ticket,
                newItem: Ticket,
            ): Boolean {
                return oldItem == newItem // !!! CHANGE THIS LATER !!!
            }

            override fun areContentsTheSame(
                oldItem: Ticket,
                newItem: Ticket,
            ): Boolean {
                return oldItem == newItem
            }
        },
    ) {
    override fun getItemCount(): Int = itemsList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return HistoryViewHolder(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = itemsList[position]
        (holder as HistoryViewHolder).bind(item)
    }
}
