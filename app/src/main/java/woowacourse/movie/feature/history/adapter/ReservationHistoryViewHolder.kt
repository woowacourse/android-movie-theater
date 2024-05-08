package woowacourse.movie.feature.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.feature.history.ui.ReservationHistoryUiModel

class ReservationHistoryViewHolder(
    private val binding: ItemReservationHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        ticket: Ticket,
        onReservationHistoryItemClickListener: ReservationHistoryItemClickListener,
    ) {
        binding.reservationHistory = ReservationHistoryUiModel.from(ticket)
        binding.root.setOnClickListener {
            onReservationHistoryItemClickListener(ticket)
        }
    }
}
