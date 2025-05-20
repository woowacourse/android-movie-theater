package woowacourse.movie.ui.view.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.ticket.TicketHistory
import woowacourse.movie.ui.view.screening.adapter.BaseViewHolder

class ReservationAdapter(private val onClick: (ticketHistory: TicketHistory) -> Unit) :
    ListAdapter<TicketHistory, BaseViewHolder<TicketHistory>>(
        object : DiffUtil.ItemCallback<TicketHistory>() {
            override fun areItemsTheSame(
                oldItem: TicketHistory,
                newItem: TicketHistory,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TicketHistory,
                newItem: TicketHistory,
            ): Boolean {
                return oldItem == newItem
            }
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<TicketHistory> {
        val binding: ItemReservationBinding =
            ItemReservationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ReservationViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<TicketHistory>,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
