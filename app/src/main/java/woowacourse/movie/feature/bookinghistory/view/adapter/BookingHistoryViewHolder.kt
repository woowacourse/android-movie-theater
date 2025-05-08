package woowacourse.movie.feature.bookinghistory.view.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryViewHolder(
    private val binding: ItemBookingHistoryBinding,
    private val handler: Handler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(bookingInfo: BookingInfoUiModel) {
        binding.bookingInfo = bookingInfo
        binding.handler = handler
    }

    interface Handler {
        fun onBookingHistoryClick(bookingInfo: BookingInfoUiModel)
    }
}
