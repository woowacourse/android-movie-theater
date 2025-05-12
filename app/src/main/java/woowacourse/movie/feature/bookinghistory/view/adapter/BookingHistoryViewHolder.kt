package woowacourse.movie.feature.bookinghistory.view.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryViewHolder(
    private val binding: ItemBookingHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        bookingHistoryDetails: BookingInfoUiModel,
        onBookingHistoryClick: (BookingInfoUiModel) -> Unit,
    ) {
        binding.bookingInfo = bookingHistoryDetails
        binding.root.setOnClickListener {
            onBookingHistoryClick(bookingHistoryDetails)
        }
    }
}
