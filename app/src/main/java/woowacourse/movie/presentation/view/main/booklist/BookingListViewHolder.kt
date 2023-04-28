package woowacourse.movie.presentation.view.main.booklist

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Reservation
import woowacourse.movie.R
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity

class BookingListViewHolder(rootView: View) :
    RecyclerView.ViewHolder(rootView) {
    private val root = rootView
    private val context = rootView.context
    private val bookingDate = rootView.findViewById<TextView>(R.id.tv_booking_date)
    private val bookingMovieTitle =
        rootView.findViewById<TextView>(R.id.tv_booking_movie_title)

    fun bind(reservation: Reservation) {
        bookingMovieTitle.text = reservation.movieTitle
        bookingDate.text = context.getString(R.string.booking_complete_date)
            .format(reservation.date.replace("-", "."), reservation.time)
        root.setOnClickListener {
            val intent = Intent(context, BookCompleteActivity::class.java)
                .putExtra(BookCompleteActivity.RESERVATION_ID_INTENT_KEY, reservation.id)
            root.context.startActivity(intent)
        }
    }
}
