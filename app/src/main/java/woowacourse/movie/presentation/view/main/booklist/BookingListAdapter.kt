package woowacourse.movie.presentation.view.main.booklist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.model.ReservationResult

class BookingListAdapter(
    private val bookings: List<ReservationResult>,
    private val clickEvent: (ReservationResult) -> Unit
) :
    RecyclerView.Adapter<BookingListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListViewHolder {
        return BookingListViewHolder(parent) {
            clickEvent(bookings[it])
        }
    }

    override fun getItemCount(): Int = bookings.size

    override fun onBindViewHolder(holder: BookingListViewHolder, position: Int) {
        holder.bind(bookings[position])
    }
}
