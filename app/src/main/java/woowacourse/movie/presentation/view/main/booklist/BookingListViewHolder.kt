package woowacourse.movie.presentation.view.main.booklist

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Reservation
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemBookingListBinding
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity

class BookingListViewHolder(binding: ItemBookingListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val root = binding.root
    private val context = binding.root.context
    private val bookingDate = binding.tvBookingDate
    private val bookingMovieTitle = binding.tvBookingMovieTitle
    fun bind(reservation: Reservation) {
        bookingMovieTitle.text = reservation.movieTitle
        bookingDate.text = context.getString(R.string.booking_complete_date)
            .format(reservation.date.replace("-", "."), reservation.time)
        root.setOnClickListener {
            val intent = Intent(context, BookCompleteActivity::class.java)
                .putExtra(BookCompleteActivity.BOOKING_COMPLETE_INFO_INTENT_KEY, reservation.id)
            root.context.startActivity(intent)
        }
    }
}