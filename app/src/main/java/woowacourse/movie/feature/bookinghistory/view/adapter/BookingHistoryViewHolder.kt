package woowacourse.movie.feature.bookinghistory.view.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryViewHolder(
    private val binding: ItemBookingHistoryBinding,
    handler: Handler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(bookingInfo: BookingInfoUiModel) {
        binding.bookingInfo = bookingInfo
    }

    interface Handler {
        fun onBookingHistoryClick(bookingInfo: BookingInfoUiModel)
    }
}
