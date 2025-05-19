package woowacourse.movie.view.history.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.BookingItemBinding
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.history.BookingHistoryEventHandler

class HistoryViewHolder private constructor(
    val binding: BookingItemBinding,
    val handler: BookingHistoryEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(
        parent: ViewGroup,
        handler: BookingHistoryEventHandler,
    ) : this(
        BookingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        handler,
    )

    fun bind(item: Ticket) {
        binding.ticket = item
        binding.handler = handler
    }
}
