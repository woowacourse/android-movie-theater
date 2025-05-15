package woowacourse.movie.feature.bookinghistory.view.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.feature.model.BookingInfoUiModel

object BookingHistoryDiffCallback : DiffUtil.ItemCallback<BookingInfoUiModel>() {
    override fun areItemsTheSame(
        oldContentItem: BookingInfoUiModel,
        newContentItem: BookingInfoUiModel,
    ): Boolean = oldContentItem.id == newContentItem.id

    override fun areContentsTheSame(
        oldContentItem: BookingInfoUiModel,
        newContentItem: BookingInfoUiModel,
    ): Boolean = oldContentItem == newContentItem
}
