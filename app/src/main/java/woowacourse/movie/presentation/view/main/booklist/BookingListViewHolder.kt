package woowacourse.movie.presentation.view.main.booklist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Reservation
import woowacourse.movie.R
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity

class BookingListViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_booking_list, parent, false)) {


    private val bookingDate: TextView = itemView.findViewById(R.id.tv_booking_date)
    private val bookingMovieTitle: TextView = itemView.findViewById(R.id.tv_booking_movie_title)

    fun bind(reservation: Reservation) {
        bookingMovieTitle.text = reservation.movieTitle
        bookingDate.text = itemView.context.getString(R.string.booking_complete_date)
            .format(reservation.date.replace("-", "."), reservation.time)
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, BookCompleteActivity::class.java)
                .putExtra(BookCompleteActivity.RESERVATION_ID_INTENT_KEY, reservation.id)
            itemView.context.startActivity(intent)
        }
    }
}
