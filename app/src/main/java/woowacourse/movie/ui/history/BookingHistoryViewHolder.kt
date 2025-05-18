package woowacourse.movie.ui.history

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HistoryItemBinding
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.utils.StringFormatter

class BookingHistoryViewHolder(
    private val binding: HistoryItemBinding,
    historyClickListener: HistoryClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.clickListener = historyClickListener
    }

    fun bind(item: BookedTicket) {
        binding.bookedTicket = item
        binding.stringFormatter = StringFormatter
    }
}
