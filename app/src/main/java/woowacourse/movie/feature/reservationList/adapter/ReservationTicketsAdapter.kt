package woowacourse.movie.feature.reservationList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationLayoutBinding
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.feature.reservationList.viewHolder.TicketsViewHolder

class ReservationTicketsAdapter(
    items: List<TicketsItemModel> = listOf(),
) : RecyclerView.Adapter<TicketsViewHolder>() {

    private var _items: List<TicketsItemModel> = items.toList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding =
            ItemReservationLayoutBinding.inflate(layoutInflater, parent, false)
        return TicketsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: TicketsViewHolder,
        position: Int
    ) {
        holder.bind(_items[position])
    }

    override fun getItemCount(): Int = _items.size

    fun setItems(items: List<TicketsItemModel>) {
        _items = items.toList()
        notifyDataSetChanged()
    }
}
