package woowacourse.movie.view.reservation.history

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationHistoryItemBinding
import woowacourse.movie.domain.Ticket

class TicketViewHolder(binding: ReservationHistoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private var ticket: Ticket? = null

    private val dateTime: TextView = binding.historyDatetimeTextview
    private val theater: TextView = binding.historyTheaterTextview
    private val title: TextView = binding.historyTitleTextview

    fun bind(ticket: Ticket) {
        this.ticket = ticket

        title.text = ticket.title
        dateTime.text = ticket.dateTime.toString()
        theater.text = ticket.theaterName
    }
}
