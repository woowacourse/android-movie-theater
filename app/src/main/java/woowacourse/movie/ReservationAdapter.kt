package woowacourse.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.ticket.Reservation

class ReservationAdapter(
    private val onSelectTicket: (Reservation) -> Unit,
) : ListAdapter<Reservation, ReservationViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemReservationBinding.inflate(layoutInflater, parent, false)
        return ReservationViewHolder(binding, onSelectTicket)
    }

    override fun onBindViewHolder(
        holder: ReservationViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), position != itemCount - 1)
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<Reservation>() {
                override fun areItemsTheSame(
                    oldItem: Reservation,
                    newItem: Reservation,
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: Reservation,
                    newItem: Reservation,
                ): Boolean = oldItem.title == newItem.title
            }
    }
}
