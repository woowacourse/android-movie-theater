package woowacourse.movie.ui.booking.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.model.db.UserTicket

object UserTicketDiffUtil : DiffUtil.ItemCallback<UserTicket>() {
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
