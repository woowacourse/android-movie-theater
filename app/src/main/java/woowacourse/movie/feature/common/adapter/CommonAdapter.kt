package woowacourse.movie.feature.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvItemLayoutBinding
import woowacourse.movie.databinding.ItemReservationLayoutBinding
import woowacourse.movie.databinding.ItemTheaterLayoutBinding
import woowacourse.movie.databinding.MovieItemLayoutBinding
import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.movieList.bottomSheet.TheaterViewHolder
import woowacourse.movie.feature.movieList.viewHolder.AdvViewHolder
import woowacourse.movie.feature.movieList.viewHolder.MovieViewHolder
import woowacourse.movie.feature.reservationList.viewHolder.TicketsViewHolder

class CommonAdapter(
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
            CommonViewType.MOVIE -> {
                val itemBinding = MovieItemLayoutBinding.inflate(layoutInflater, parent, false)
                MovieViewHolder(itemBinding)
            }
            CommonViewType.ADV -> {
                val itemBinding = AdvItemLayoutBinding.inflate(layoutInflater, parent, false)
                AdvViewHolder(itemBinding)
            }
            CommonViewType.RESERVATION -> {
                val itemBinding =
                    ItemReservationLayoutBinding.inflate(layoutInflater, parent, false)
                TicketsViewHolder(itemBinding)
            }
            CommonViewType.THEATER -> {
                val itemBinding = ItemTheaterLayoutBinding.inflate(layoutInflater, parent, false)
                TheaterViewHolder(itemBinding)
            }
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
