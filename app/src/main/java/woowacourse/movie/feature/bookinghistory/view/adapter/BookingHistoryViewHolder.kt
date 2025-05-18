package woowacourse.movie.feature.bookinghistory.view.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryViewHolder(
    private val binding: ItemBookingHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        BookingInfo: BookingInfoUiModel,
        onBookingHistoryClick: (BookingInfoUiModel) -> Unit,
    ) {
        binding.bookingInfo = BookingInfo
        binding.root.setOnClickListener {
            onBookingHistoryClick(BookingInfo)
        }
    }
}
