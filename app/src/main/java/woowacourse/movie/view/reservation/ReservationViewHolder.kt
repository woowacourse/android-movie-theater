package woowacourse.movie.view.reservation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.ticket.Reservation

class ReservationViewHolder(
    private val binding: ItemReservationBinding,
    private val onSelectTicket: (Reservation) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        reservation: Reservation,
        needDivider: Boolean,
    ) {
        bindData(reservation, needDivider)
    }

    private fun bindData(
        reservation: Reservation,
        needDivider: Boolean,
    ) {
        binding.showtime = reservation.showtime
        binding.cinemaName = "극장 이름"
        binding.title = reservation.title
        binding.needDivider = needDivider
        binding.onClickItemListener = View.OnClickListener { onSelectTicket(reservation) }
    }
}
