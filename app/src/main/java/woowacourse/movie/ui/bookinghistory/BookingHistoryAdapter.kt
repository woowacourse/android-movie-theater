package woowacourse.movie.ui.bookinghistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.BookingHistoryItemBinding
import woowacourse.movie.model.ReservationUiModel

class BookingHistoryAdapter(
    private val clickItem: (reservation: ReservationUiModel) -> Unit,
) : RecyclerView.Adapter<BookingHistoryViewHolder>() {
    private val bookingHistory = mutableListOf<ReservationUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BookingHistoryItemBinding.inflate(layoutInflater, parent, false)

        return BookingHistoryViewHolder(binding).apply {
            setOnReservationClickListener(onClicked = clickItem)
        }
    }

    override fun onBindViewHolder(holder: BookingHistoryViewHolder, position: Int) {
        holder.bind(bookingHistory[position])
    }

    override fun getItemCount(): Int {
        return bookingHistory.size
    }

    fun initList(items: List<ReservationUiModel>) {
        bookingHistory.clear()
        bookingHistory.addAll(items)
        notifyDataSetChanged()
    }
}
