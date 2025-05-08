package woowacourse.movie.feature.bookinghistory.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryAdapter(
    private val items: List<BookingInfoUiModel>,
    private val handler: Handler,
) : RecyclerView.Adapter<BookingHistoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookingHistoryViewHolder {
        val binding = ItemBookingHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingHistoryViewHolder(binding, handler)
    }

    override fun onBindViewHolder(
        holder: BookingHistoryViewHolder,
        position: Int,
    ) {
        val item: BookingInfoUiModel = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    interface Handler : BookingHistoryViewHolder.Handler
}
