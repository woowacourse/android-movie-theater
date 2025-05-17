package woowacourse.movie.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.data.BookedTicketEntity
import woowacourse.movie.databinding.HistoryItemBinding

class BookingHistoryAdapter(
    private val historyClickListener: HistoryClickListener,
) : ListAdapter<BookedTicketEntity, BookingHistoryViewHolder>(
        object : DiffUtil.ItemCallback<BookedTicketEntity>() {
            override fun areItemsTheSame(
                oldItem: BookedTicketEntity,
                newItem: BookedTicketEntity,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BookedTicketEntity,
                newItem: BookedTicketEntity,
            ): Boolean {
                return oldItem == newItem
            }
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookingHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryItemBinding.inflate(inflater, parent, false)
        return BookingHistoryViewHolder(binding, historyClickListener)
    }

    override fun onBindViewHolder(
        holder: BookingHistoryViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}
