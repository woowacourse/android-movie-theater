package woowacourse.movie.feature.bookinghistory

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.feature.model.BookingInfoUiModel

object BookingHistoryDiffCallback : DiffUtil.ItemCallback<BookingInfoUiModel>() {
    override fun areItemsTheSame(
        oldItem: BookingInfoUiModel,
        newItem: BookingInfoUiModel,
    ): Boolean = oldItem.uid == newItem.uid

    override fun areContentsTheSame(
        oldItem: BookingInfoUiModel,
        newItem: BookingInfoUiModel,
    ): Boolean = oldItem == newItem
}
