package woowacourse.movie.feature.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.common.itemModel.ViewType
import woowacourse.movie.feature.common.viewHolder.AdvViewHolder
import woowacourse.movie.feature.common.viewHolder.ItemViewHolder
import woowacourse.movie.feature.common.viewHolder.MovieViewHolder
import woowacourse.movie.feature.common.viewHolder.TheaterViewHolder
import woowacourse.movie.feature.common.viewHolder.TicketsViewHolder

class CommonListAdapter(
    private var items: List<ItemModel> = listOf()
) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemType: ViewType = ViewType.of(viewType)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, itemType.layoutRes, parent, false)

        return when (itemType) {
            ViewType.MOVIE -> MovieViewHolder(binding)
            ViewType.ADV -> AdvViewHolder(binding)
            ViewType.TICKETS -> TicketsViewHolder(binding)
            ViewType.THEATER -> TheaterViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    fun setItems(items: List<ItemModel>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
