package woowacourse.movie.presentation.view.main.booklist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Reservation
import woowacourse.movie.presentation.model.Movie

class BookingListAdapter(
    private val reservations: List<Reservation>,
    private val clickEvent: (Reservation) -> Unit
) :
    RecyclerView.Adapter<BookingListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListViewHolder {
        return BookingListViewHolder(parent){
            clickEvent(reservations[it])
        }
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(holder: BookingListViewHolder, position: Int) {
        holder.bind(reservations[position])
    }
}
