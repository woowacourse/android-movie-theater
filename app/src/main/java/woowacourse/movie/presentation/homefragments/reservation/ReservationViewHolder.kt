package woowacourse.movie.presentation.homefragments.reservation

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.model.Reservation

class ReservationViewHolder(private val binding: ItemReservationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        reservation: Reservation,
        listener: ReservationItemClickListener,
    ) {
        binding.reservation = reservation
        binding.reservationItemClickListener = listener
    }
}
