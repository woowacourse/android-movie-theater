package woowacourse.movie.view.history.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.BookingItemBinding
import woowacourse.movie.domain.model.ticket.Ticket

class HistoryViewHolder(
    val binding: BookingItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup) : this(
        BookingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    fun bind(item: Ticket) {
        binding.ticket = item
    }
}
