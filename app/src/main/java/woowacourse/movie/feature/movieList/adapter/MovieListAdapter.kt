package woowacourse.movie.feature.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvItemLayoutBinding
import woowacourse.movie.databinding.ItemTheaterLayoutBinding
import woowacourse.movie.databinding.MovieItemLayoutBinding
import woowacourse.movie.feature.common.ViewType
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.common.viewHolder.ItemViewHolder
import woowacourse.movie.feature.movieList.bottomSheet.TheaterViewHolder
import woowacourse.movie.feature.movieList.viewHolder.AdvViewHolder
import woowacourse.movie.feature.movieList.viewHolder.MovieViewHolder

class MovieListAdapter(
    items: List<ItemModel>,
) : RecyclerView.Adapter<ItemViewHolder>() {

    private var _items: List<ItemModel> = items.toList()
    val items: List<ItemModel>
        get() = _items.toList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (ViewType.of(viewType)) {
            ViewType.MOVIE -> {
                val itemBinding = MovieItemLayoutBinding.inflate(layoutInflater, parent, false)
                MovieViewHolder(itemBinding)
            }
            ViewType.ADV -> {
                val itemBinding = AdvItemLayoutBinding.inflate(layoutInflater, parent, false)
                AdvViewHolder(itemBinding)
            }
            ViewType.THEATER -> {
                val itemBinding = ItemTheaterLayoutBinding.inflate(layoutInflater, parent, false)
                TheaterViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.bind(_items[position])
    }

    override fun getItemCount(): Int = _items.size

    override fun getItemViewType(position: Int): Int {
        return _items[position].viewType.ordinal
    }

    fun setItems(items: List<ItemModel>) {
        _items = items.toList()
        notifyDataSetChanged()
    }
}
