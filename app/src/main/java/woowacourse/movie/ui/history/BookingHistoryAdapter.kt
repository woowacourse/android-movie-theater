package woowacourse.movie.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.HistoryItemBinding
import woowacourse.movie.domain.model.BookedTicket

class BookingHistoryAdapter(
    private val historyClickListener: HistoryClickListener,
) : ListAdapter<BookedTicket, BookingHistoryViewHolder>(
        object : DiffUtil.ItemCallback<BookedTicket>() {
            override fun areItemsTheSame(
                oldItem: BookedTicket,
                newItem: BookedTicket,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BookedTicket,
                newItem: BookedTicket,
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
