package woowacourse.movie.ui.booking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemBookedMovieBinding
import woowacourse.movie.model.db.UserTicket
import woowacourse.movie.ui.booking.UserTicketDiffUtil

class MovieBookingHistoryAdapter(
    private val bookingHistoryClickListener: (Long) -> Unit,
) : ListAdapter<UserTicket, MovieBookingHistoryViewHolder>(UserTicketDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieBookingHistoryViewHolder {
        val binding: ItemBookedMovieBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_booked_movie,
                parent,
                false,
            )
        return MovieBookingHistoryViewHolder(binding, bookingHistoryClickListener)
    }

    override fun onBindViewHolder(
        holder: MovieBookingHistoryViewHolder,
        position: Int,
    ) {
        val userTicket = getItem(position)
        holder.bind(userTicket)
    }
}
