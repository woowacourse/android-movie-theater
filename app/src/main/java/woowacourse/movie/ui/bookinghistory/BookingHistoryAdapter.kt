package woowacourse.movie.ui.bookinghistory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.model.ReservationUiModel

class BookingHistoryAdapter(
    private val clickItem: (reservation: ReservationUiModel) -> Unit,
) : RecyclerView.Adapter<BookingHistoryViewHolder>() {
    private val bookingHistory = mutableListOf<ReservationUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHistoryViewHolder {

        return BookingHistoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BookingHistoryViewHolder, position: Int) {
        holder.bind(bookingHistory[position])
        holder.setOnReservationClickListener(clickItem)
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
