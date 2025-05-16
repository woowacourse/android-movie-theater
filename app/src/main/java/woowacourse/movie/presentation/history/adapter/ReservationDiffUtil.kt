package woowacourse.movie.presentation.history.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.presentation.common.model.ReservationHistoryUiModel

object ReservationDiffUtil : DiffUtil.ItemCallback<ReservationHistoryUiModel>() {
    override fun areItemsTheSame(
        oldItem: ReservationHistoryUiModel,
        newItem: ReservationHistoryUiModel,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ReservationHistoryUiModel,
        newItem: ReservationHistoryUiModel,
    ): Boolean = oldItem == newItem
}
