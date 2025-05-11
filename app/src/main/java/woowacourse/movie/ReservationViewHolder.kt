package woowacourse.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.ticket.Reservation
import java.time.LocalDateTime

class ReservationViewHolder(
    private val binding: ItemReservationBinding,
    private val onSelectTicket: (Reservation) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var showtime: LocalDateTime? = null
    private var cinemaName: String = ""
    private var title: String = ""
    private var needDivider: Boolean = true

    fun bind(
        reservation: Reservation,
        needDivider: Boolean,
    ) {
        initData(reservation, needDivider)
        bindData()
        initEventListeners(reservation)
    }

    private fun initData(
        reservation: Reservation,
        _needDivider: Boolean,
    ) {
        showtime = reservation.showtime
        cinemaName = "극장 이름"
        title = reservation.title
        needDivider = _needDivider
    }

    private fun bindData() {
        binding.showtime = showtime
        binding.cinemaName = cinemaName
        binding.title = title
        binding.needDivider = needDivider
    }

    private fun initEventListeners(reservation: Reservation) {
        binding.root.setOnClickListener {
            onSelectTicket(reservation)
        }
    }
}
