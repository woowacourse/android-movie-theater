package woowacourse.movie.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.model.movie.UserTicket

class BookingHistoryAdapter(
    private val bookingHistoryClickListener: BookingHistoryClickListener,
) :
    ListAdapter<UserTicket, BookingHistoryAdapter.BookingHistoryViewHolder>(BookingHistoryDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHistoryViewHolder {
        val binding = DataBindingUtil.inflate<ItemBookingHistoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_booking_history,
            parent,
            false
        )

        return BookingHistoryViewHolder(binding, bookingHistoryClickListener)
    }

    override fun onBindViewHolder(holder: BookingHistoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class BookingHistoryViewHolder(
        val binding: ItemBookingHistoryBinding,
        bookingHistoryClickListener: BookingHistoryClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                binding.ticket?.id?.let {
                    bookingHistoryClickListener.onBookingHistoryClick(it)
                }
            }
        }

        fun bind(userTicket: UserTicket) {
            binding.ticket = userTicket
        }
    }

    interface BookingHistoryClickListener {
        fun onBookingHistoryClick(ticketId: Long)
    }
}

object BookingHistoryDiffUtil : DiffUtil.ItemCallback<UserTicket>() {
    override fun areItemsTheSame(oldItem: UserTicket, newItem: UserTicket): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserTicket, newItem: UserTicket): Boolean {
        return oldItem == newItem
    }
}
