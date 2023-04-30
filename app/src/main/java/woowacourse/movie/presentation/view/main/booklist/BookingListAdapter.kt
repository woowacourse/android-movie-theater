package woowacourse.movie.presentation.view.main.booklist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Reservation

class BookingListAdapter(private val reservations: List<Reservation>) :
    RecyclerView.Adapter<BookingListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListViewHolder {
        return BookingListViewHolder(parent)
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(holder: BookingListViewHolder, position: Int) {
        holder.bind(reservations[position])
    }
}
