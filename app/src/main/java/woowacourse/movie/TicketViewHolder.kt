package woowacourse.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTicketBinding
import woowacourse.movie.domain.ticket.Ticket
import java.time.LocalDateTime

class TicketViewHolder(
    private val binding: ItemTicketBinding,
    private val onSelectTicket: (Ticket) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var showtime: LocalDateTime? = null
    private var cinemaName: String = ""
    private var title: String = ""
    private var needDivider: Boolean = true

    fun bind(
        ticket: Ticket,
        needDivider: Boolean,
    ) {
        initData(ticket, needDivider)
        bindData()
        initEventListeners(ticket)
    }

    private fun initData(
        ticket: Ticket,
        _needDivider: Boolean,
    ) {
        showtime = ticket.showtime
        cinemaName = "극장 이름"
        title = ticket.title
        needDivider = _needDivider
    }

    private fun bindData() {
        binding.showtime = showtime
        binding.cinemaName = cinemaName
        binding.title = title
        binding.needDivider = needDivider
    }

    private fun initEventListeners(ticket: Ticket) {
        binding.root.setOnClickListener {
            onSelectTicket(ticket)
        }
    }
}
