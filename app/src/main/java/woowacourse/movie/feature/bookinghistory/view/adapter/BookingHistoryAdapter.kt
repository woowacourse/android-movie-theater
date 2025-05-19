package woowacourse.movie.feature.bookinghistory.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryAdapter(
    private val onBookingHistoryClick: (BookingInfoUiModel) -> Unit,
) : ListAdapter<BookingInfoUiModel, BookingHistoryViewHolder>(
        BookingHistoryDiffCallback,
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookingHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookingHistoryBinding.inflate(inflater, parent, false)
        return BookingHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BookingHistoryViewHolder,
        position: Int,
    ) {
        val item: BookingInfoUiModel = getItem(position)
        holder.bind(item, onBookingHistoryClick)
    }
}
