package woowacourse.movie.presentation.ui.main.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderReservationHistoryBinding
import woowacourse.movie.domain.model.Reservation

class ReservationHistoryRecyclerViewHolder(private val binding: HolderReservationHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(reservation: Reservation) {
        binding.tvHistoryDate.text = reservation.dateTime.toString()
        binding.tvHistoryTime.text = reservation.dateTime.toString()
        binding.tvHistoryTheater.text = reservation.theaterName
        binding.tvHistoryTitle.text = reservation.movieTitle
    }
}
