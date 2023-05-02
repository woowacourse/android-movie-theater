package woowacourse.movie.feature.reservationList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationLayoutBinding
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.feature.reservationList.viewHolder.TicketsViewHolder

class ReservationListAdapter(
    reservations: List<TicketsItemModel>
) : RecyclerView.Adapter<TicketsViewHolder>() {

    private var _reservations = reservations.toList()
    val reservations
        get() = _reservations.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemReservationLayoutBinding.inflate(layoutInflater, parent, false)
        return TicketsViewHolder(itemView)
    }

    override fun getItemCount(): Int = _reservations.size

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) {
        holder.bind(_reservations[position])
    }

    fun setItemChanged(newReservations: List<TicketsItemModel>) {
        _reservations = newReservations.toList()
        notifyDataSetChanged()
    }
}
