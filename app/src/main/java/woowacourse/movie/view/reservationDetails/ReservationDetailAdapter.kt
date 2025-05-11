package woowacourse.movie.view.reservationDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationDetailBinding
import woowacourse.movie.model.ticket.MovieTicket

class ReservationDetailAdapter(
    private val reservationDetailClickListener: ReservationDetailClickListener,
) : ListAdapter<MovieTicket, ReservationDetailViewHolder>(
        object : DiffUtil.ItemCallback<MovieTicket>() {
            override fun areItemsTheSame(
                oldItem: MovieTicket,
                newItem: MovieTicket,
            ): Boolean = oldItem.ticketId == newItem.ticketId

            override fun areContentsTheSame(
                oldItem: MovieTicket,
                newItem: MovieTicket,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val reservationBinding =
            DataBindingUtil.inflate<ItemReservationDetailBinding>(
                inflater,
                R.layout.item_reservation_detail,
                parent,
                false,
            )
        val holder = ReservationDetailViewHolder(reservationBinding)
        onReservationDetailButtonClicked(holder)
        return holder
    }

    override fun onBindViewHolder(
        holder: ReservationDetailViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item, reservationDetailClickListener)
    }

    private fun onReservationDetailButtonClicked(holder: ReservationDetailViewHolder) {
        holder.button.setOnClickListener {
            val position = holder.adapterPosition
            val item = getItem(position)
            reservationDetailClickListener.onReservationClick(item.ticketId)
        }
    }
}
