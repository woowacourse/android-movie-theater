package woowacourse.movie.presentation.history.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.presentation.common.model.TicketUiModel

object ReservationDiffUtil : DiffUtil.ItemCallback<TicketUiModel>() {
    override fun areItemsTheSame(
        oldItem: TicketUiModel,
        newItem: TicketUiModel,
    ): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(
        oldItem: TicketUiModel,
        newItem: TicketUiModel,
    ): Boolean = oldItem == newItem
}
