package woowacourse.movie.ui.booking.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemBookedMovieBinding
import woowacourse.movie.model.db.UserTicket

class MovieBookingHistoryViewHolder(
    private val binding: ItemBookedMovieBinding,
    private val bookingHistoryClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(userTicket: UserTicket) {
        binding.userTicket = userTicket
        binding.bookedMovie.setOnClickListener {
            bookingHistoryClickListener(userTicket.id)
        }
    }
}
