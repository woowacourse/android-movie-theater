package woowacourse.movie.presentation.view.main.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemBookingListBinding
import woowacourse.movie.presentation.model.ReservationResult

class BookingListViewHolder(
    private val parent: ViewGroup,
    private val event: (Int) -> Unit
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking_list, parent, false)
    ) {
    private val binding = ItemBookingListBinding.bind(itemView)

    init {
        itemView.setOnClickListener {
            event(adapterPosition)
        }
    }

    fun bind(bookings: ReservationResult) {
        binding.reservation = bookings
        binding.tvBookingMovieTitle.text = bookings.movieTitle
    }
}
