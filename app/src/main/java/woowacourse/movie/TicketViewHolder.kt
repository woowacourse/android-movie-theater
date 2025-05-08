package woowacourse.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTicketBinding
import woowacourse.movie.domain.ticket.Ticket
import java.time.LocalDateTime

class TicketViewHolder(
    private val binding: ItemTicketBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var showtime: LocalDateTime? = null
    private var cinemaName: String = ""
    private var title: String = ""

    fun bind(ticket: Ticket) {
        initData(ticket)
        bindData()
    }

    private fun initData(ticket: Ticket) {
        showtime = ticket.showtime
        cinemaName = "극장 이름"
        title = ticket.title
    }

    private fun bindData() {
        binding.showtime = showtime
        binding.cinemaName = cinemaName
        binding.title = title
    }
}
