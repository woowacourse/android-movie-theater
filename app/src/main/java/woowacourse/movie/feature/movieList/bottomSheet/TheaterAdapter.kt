package woowacourse.movie.feature.movieList.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.movieList.viewHolder.AdvViewHolder
import woowacourse.movie.feature.movieList.viewHolder.MovieViewHolder
import woowacourse.movie.feature.reservationList.viewHolder.TicketsViewHolder

class TheaterAdapter(
    items: List<TheaterItemModel> = listOf(),
) : RecyclerView.Adapter<CommonItemViewHolder>() {

    private var _items: List<TheaterItemModel> = items.toList()
    val items: List<TheaterItemModel>
        get() = _items.toList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            layoutInflater,
            CommonViewType.of(viewType).layoutRes,
            parent,
            false
        )
        return when (CommonViewType.of(viewType)) {
            CommonViewType.MOVIE -> MovieViewHolder(binding)
            CommonViewType.ADV -> AdvViewHolder(binding)
            CommonViewType.RESERVATION -> TicketsViewHolder(binding)
            CommonViewType.THEATER -> TheaterViewHolder(binding)
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

    fun setItems(items: List<TheaterItemModel>) {
        _items = items.toList()
        notifyDataSetChanged()
    }
}
