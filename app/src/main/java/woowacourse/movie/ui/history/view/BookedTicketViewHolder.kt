package woowacourse.movie.ui.history.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.BookedTicketItemBinding
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.utils.StringFormatter

class BookedTicketViewHolder(
    val binding: BookedTicketItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(bookedTicket: BookedTicket) {
        binding.stringFormatter = StringFormatter
        binding.bookedTicket = bookedTicket
    }

    companion object {
        fun from(parent: ViewGroup): BookedTicketViewHolder {
            val binding =
                BookedTicketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BookedTicketViewHolder(binding)
        }
    }
}
