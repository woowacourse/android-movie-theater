package woowacourse.movie.presentation.view.main.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Reservation
import woowacourse.movie.R

class BookingListAdapter(private val reservations: List<Reservation>) :
    RecyclerView.Adapter<BookingListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_booking_list, parent, false)
        return BookingListViewHolder(view)
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(holder: BookingListViewHolder, position: Int) {
        holder.bind(reservations[position])
    }
}
