package woowacourse.movie.presentation.ui.main.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderReservationHistoryBinding
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.presentation.ui.main.history.ReservationHistoryActionHandler

class ReservationHistoryRecyclerViewHolder(
    private val binding: HolderReservationHistoryBinding,
    private val handler: ReservationHistoryActionHandler,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(reservation: Reservation) {
        binding.handler = handler
        binding.reservation = reservation
    }
}
