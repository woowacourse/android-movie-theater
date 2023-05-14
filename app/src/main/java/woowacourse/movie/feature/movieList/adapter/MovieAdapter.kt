package woowacourse.movie.feature.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.feature.movieList.CommonViewType
import woowacourse.movie.feature.movieList.itemModel.CommonItemModel
import woowacourse.movie.feature.movieList.viewHolder.AdvViewHolder
import woowacourse.movie.feature.movieList.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.movieList.viewHolder.MovieViewHolder

class MovieAdapter(
    items: List<CommonItemModel> = listOf()
) : RecyclerView.Adapter<CommonItemViewHolder>() {

    private var _items: List<CommonItemModel> = items.toList()

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
