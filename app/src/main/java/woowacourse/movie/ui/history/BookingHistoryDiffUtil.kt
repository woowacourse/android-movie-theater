package woowacourse.movie.ui.history

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.domain.UserTicket

object BookingHistoryDiffUtil : DiffUtil.ItemCallback<UserTicket>() {
    override fun areItemsTheSame(
        oldItem: UserTicket,
        newItem: UserTicket,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserTicket,
        newItem: UserTicket,
    ): Boolean {
        return oldItem == newItem
    }
}
