package woowacourse.movie.view.home.movies.adapter

import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationHistoryItemBinding
import woowacourse.movie.view.reservation.TicketUi
import woowacourse.movie.view.reservation.history.OnReservationEventListener

class ReservationViewHolder(
    eventListener: OnReservationEventListener,
    val binding: ReservationHistoryItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var ticket: TicketUi? = null

    init {
        binding.onItemClick =
            OnClickListener { ticket?.let { eventListener.onClickReservation(adapterPosition) } }
    }

    fun bind(ticket: TicketUi) {
        this.ticket = ticket
        binding.ticket = ticket
    }
}
