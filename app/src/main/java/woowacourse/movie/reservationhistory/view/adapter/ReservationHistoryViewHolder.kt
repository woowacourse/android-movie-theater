package woowacourse.movie.reservationhistory.view.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.ReservationHistoryEntity
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.reservationhistory.view.listener.ReservationHistoryClickListener

class ReservationHistoryViewHolder(private val binding: ItemReservationHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        reservationHistoryEntity: ReservationHistoryEntity,
        reservationHistoryClickListener: ReservationHistoryClickListener,
    ) {
        binding.reservationHistoryClickListener = reservationHistoryClickListener
        binding.reservationHistoryEntity = reservationHistoryEntity
    }
}
