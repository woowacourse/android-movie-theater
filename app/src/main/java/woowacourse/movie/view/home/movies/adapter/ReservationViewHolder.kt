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
    init {
        binding.onItemClick =
            OnClickListener { eventListener.onClickReservation(adapterPosition) }
    }

    fun bind(ticket: TicketUi) {
        binding.ticket = ticket
    }
}
