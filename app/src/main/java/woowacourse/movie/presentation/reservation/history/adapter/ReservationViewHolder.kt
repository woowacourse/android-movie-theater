package woowacourse.movie.presentation.reservation.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationItemBinding
import woowacourse.movie.presentation.uimodel.TicketUiModel
import woowacourse.movie.presentation.reservation.history.ReservationListContract

class ReservationViewHolder(
    private val binding: ReservationItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        ticket: TicketUiModel,
        clickListener: ReservationListContract.ItemListener,
    ) {
        binding.ticket = ticket
        binding.listener = clickListener
    }
}
