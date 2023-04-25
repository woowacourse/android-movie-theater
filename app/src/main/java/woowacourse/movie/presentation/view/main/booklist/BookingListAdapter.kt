package woowacourse.movie.presentation.view.main.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Reservation
import woowacourse.movie.databinding.ItemBookingListBinding

class BookingListAdapter(private val reservations: List<Reservation>) :
    RecyclerView.Adapter<BookingListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListViewHolder {
        val binding =
            ItemBookingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingListViewHolder(binding)
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(holder: BookingListViewHolder, position: Int) {
        holder.bind(reservations[position])
    }
}