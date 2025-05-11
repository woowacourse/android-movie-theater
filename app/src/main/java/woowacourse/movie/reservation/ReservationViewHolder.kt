package woowacourse.movie.reservation

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.Reservation
import woowacourse.movie.databinding.ReservationItemBinding

class ReservationViewHolder(
    private val binding: ReservationItemBinding,
    reservationClickListener: ReservationClickListener,
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.reservationClickListener = reservationClickListener
    }

    fun bind(reservation: Reservation) {
        binding.reservation = reservation
    }
}
