package woowacourse.movie.ui.history.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.theater.BookedTicket

class BookedTicketAdapter(
    val onClickBookedTicket: BookedTicketClickListener,
) : ListAdapter<BookedTicket, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<BookedTicket>() {
            override fun areItemsTheSame(
                oldItem: BookedTicket,
                newItem: BookedTicket,
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: BookedTicket,
                newItem: BookedTicket,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder = BookedTicketViewHolder.from(parent, onClickBookedTicket)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as BookedTicketViewHolder).bind(currentList[position])
    }
}
