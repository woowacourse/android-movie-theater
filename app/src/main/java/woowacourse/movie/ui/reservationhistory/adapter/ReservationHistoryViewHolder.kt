package woowacourse.movie.ui.reservationhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderScreenReservationHistoryBinding
import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.ui.reservationhistory.ReservationHistoryActionHandler

class ReservationHistoryViewHolder(
    private val binding: HolderScreenReservationHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        reservationHistory: ReservationHistory,
        reservationHistoryActionHandler: ReservationHistoryActionHandler,
    ) {
        binding.reservationHistory = reservationHistory
        binding.reservationHistoryActionHandler = reservationHistoryActionHandler
    }
}
