package woowacourse.movie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.domain.model.movie.MovieTicket

class BookingHistoryAdapter(
    private val onClick: (MovieTicket) -> Unit,
) : ListAdapter<MovieTicket, BookingHistoryAdapter.BookingHistoryViewHolder>(diffCallBack) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookingHistoryViewHolder {
        return BookingHistoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_booking_history,
                parent,
                false,
            ),
            onClick,
        )
    }

    override fun onBindViewHolder(
        holder: BookingHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class BookingHistoryViewHolder(
        private val binding: ItemBookingHistoryBinding,
        private val onClick: (MovieTicket) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieTicket: MovieTicket) {
            binding.ticket = movieTicket
            binding.listener = ClickListener<MovieTicket> { onClick(it) }
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
                ): Boolean = oldItem.movieTitle == newItem.movieTitle && oldItem.screeningDateTime == newItem.screeningDateTime
            }
    }
}
