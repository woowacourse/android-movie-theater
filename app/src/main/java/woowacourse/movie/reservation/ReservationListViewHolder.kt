package woowacourse.movie.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationListItemBinding
import woowacourse.movie.ui.model.TicketUiModel

class ReservationListViewHolder(
    private val binding: ReservationListItemBinding,
    private val onReservationClick: ReservationClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(reservation: TicketUiModel) {
        binding.reservation = reservation
        binding.clickListener = onReservationClick
    }

    companion object {
        fun from(
            parent: ViewGroup,
            clickListener: ReservationClickListener,
        ): ReservationListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ReservationListItemBinding.inflate(inflater, parent, false)
            return ReservationListViewHolder(binding, clickListener)
        }
    }
}
