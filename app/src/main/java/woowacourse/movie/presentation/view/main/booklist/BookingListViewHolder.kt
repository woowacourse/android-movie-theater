package woowacourse.movie.presentation.view.main.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.ReservationResult

class BookingListViewHolder(
    private val parent: ViewGroup,
    private val event: (Int) -> Unit
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking_list, parent, false)
    ) {

    private val bookingDate: TextView = itemView.findViewById(R.id.tv_booking_date)
    private val bookingMovieTitle: TextView = itemView.findViewById(R.id.tv_booking_movie_title)

    init {
        itemView.setOnClickListener {
            event(adapterPosition)
        }
    }

    fun bind(bookings: ReservationResult) {
        bookingMovieTitle.text = bookings.movieTitle
        bookingDate.text = itemView.context.getString(R.string.booking_complete_date)
            .format(bookings.date.replace("-", "."), bookings.time)
    }
}
