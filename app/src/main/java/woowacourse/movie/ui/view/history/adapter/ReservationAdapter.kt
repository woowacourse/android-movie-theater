package woowacourse.movie.ui.view.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.screening.adapter.BaseViewHolder

class ReservationAdapter :
    ListAdapter<Ticket, BaseViewHolder<Ticket>>(
        object : DiffUtil.ItemCallback<Ticket>() {
            override fun areItemsTheSame(
                oldItem: Ticket,
                newItem: Ticket,
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: Ticket,
                newItem: Ticket,
            ): Boolean {
                return oldItem == newItem
            }
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<Ticket> {
        val binding: ItemReservationBinding =
            ItemReservationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<Ticket>,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
