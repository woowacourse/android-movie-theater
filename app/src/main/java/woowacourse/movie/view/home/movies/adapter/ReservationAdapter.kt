package woowacourse.movie.view.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ReservationHistoryItemBinding
import woowacourse.movie.view.reservation.Ticket
import woowacourse.movie.view.reservation.history.OnReservationEventListener

class ReservationAdapter(
    private val eventListener: OnReservationEventListener,
) : ListAdapter<Ticket, ReservationViewHolder>(ReservationItemDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ReservationHistoryItemBinding = ReservationHistoryItemBinding.inflate(inflater, parent, false)

        return ReservationViewHolder(eventListener, binding)
    }

    override fun onBindViewHolder(
        holder: ReservationViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val ReservationItemDiffCallback =
            object : DiffUtil.ItemCallback<Ticket>() {
                override fun areItemsTheSame(
                    oldItem: Ticket,
                    newItem: Ticket,
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: Ticket,
                    newItem: Ticket,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
