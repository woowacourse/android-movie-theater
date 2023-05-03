package woowacourse.app.ui.main.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.model.ReservationUiModel
import woowacourse.movie.R

class BookingHistoryAdapter(
    private val clickItem: (id: Long) -> Unit,
) : RecyclerView.Adapter<BookingHistoryViewHolder>() {
    private val bookingHistory = mutableListOf<ReservationUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater.inflate(R.layout.booking_history_item, parent, false)

        return BookingHistoryViewHolder(view).apply { itemClicked(clickItem) }
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
