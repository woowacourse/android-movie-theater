package woowacourse.movie.feature.reservationList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationLayoutBinding
import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.reservationList.viewHolder.TicketsViewHolder

class ReservationTicketsAdapter(
    items: List<CommonItemModel> = listOf(),
) : RecyclerView.Adapter<CommonItemViewHolder>() {

    private var _items: List<CommonItemModel> = items.toList()
    val items: List<CommonItemModel>
        get() = _items.toList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (CommonViewType.of(viewType)) {
            CommonViewType.RESERVATION -> {
                val itemBinding =
                    ItemReservationLayoutBinding.inflate(layoutInflater, parent, false)
                TicketsViewHolder(itemBinding)
            }
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(
        holder: CommonItemViewHolder,
        position: Int
    ) {
        holder.bind(_items[position])
    }

    override fun getItemCount(): Int = _items.size

    override fun getItemViewType(position: Int): Int {
        return _items[position].viewType.ordinal
    }

    fun setItems(items: List<CommonItemModel>) {
        _items = items.toList()
        notifyDataSetChanged()
    }
}
