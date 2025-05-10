package woowacourse.movie.reservation

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationItemBinding
import woowacourse.movie.domain.ReservationInfo

class ReservationViewHolder(
    private val binding: ReservationItemBinding,
    reservationClickListener: ReservationClickListener,
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.reservationClickListener = reservationClickListener
    }

    fun bind(reservationInfo: ReservationInfo) {
        binding.reservationInfo = reservationInfo
    }
}
