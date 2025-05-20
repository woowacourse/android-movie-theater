package woowacourse.movie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.domain.model.movie.MovieTicket

class BookingHistoryAdapter(
    private val onClick: ClickListener<MovieTicket>,
) : ListAdapter<MovieTicket, BookingHistoryAdapter.BookingHistoryViewHolder>(diffCallBack) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookingHistoryViewHolder {
        return BookingHistoryViewHolder(parent, onClick)
    }

    override fun onBindViewHolder(
        holder: BookingHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class BookingHistoryViewHolder(
        parent: ViewGroup,
        onClick: ClickListener<MovieTicket>,
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_booking_history, parent, false)
    ) {
        private val binding = ItemBookingHistoryBinding.bind(itemView)

        init {
            binding.listener = onClick
        }

        fun bind(movieTicket: MovieTicket) {
            binding.ticket = movieTicket
        }
    }

    companion object {
        private val diffCallBack =
            object : ItemCallback<MovieTicket>() {
                override fun areContentsTheSame(
                    oldItem: MovieTicket,
                    newItem: MovieTicket,
                ): Boolean = oldItem == newItem

                override fun areItemsTheSame(
                    oldItem: MovieTicket,
                    newItem: MovieTicket,
                ): Boolean =
                    oldItem.movieTitle == newItem.movieTitle && oldItem.screeningDateTime == newItem.screeningDateTime
            }
    }
}
