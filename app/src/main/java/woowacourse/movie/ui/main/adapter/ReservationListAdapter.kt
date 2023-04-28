package woowacourse.movie.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.ui.main.itemModel.ItemModel
import woowacourse.movie.ui.main.itemModel.TicketsItemModel
import woowacourse.movie.ui.main.viewHolder.ItemViewHolder
import woowacourse.movie.ui.main.viewHolder.TicketsViewHolder

class ReservationListAdapter(
    reservations: List<ItemModel>,
    private val onReservationClick: (TicketsItemModel) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {

    private var reservations = reservations.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation_layout, parent, false)
        return TicketsViewHolder(itemView) { position ->
            onReservationClick(reservations[position] as TicketsItemModel)
        }
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(reservations[position])
    }

    fun setItemChanged(newReservations: List<ItemModel>) {
        reservations = newReservations.toList()
        notifyDataSetChanged()
    }
}
