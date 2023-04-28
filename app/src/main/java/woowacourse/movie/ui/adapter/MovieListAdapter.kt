package woowacourse.movie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.ui.itemModel.ViewType
import woowacourse.movie.ui.itemModel.AdvItemModel
import woowacourse.movie.ui.itemModel.ItemModel
import woowacourse.movie.ui.itemModel.MovieItemModel
import woowacourse.movie.ui.viewHolder.AdvViewHolder
import woowacourse.movie.ui.viewHolder.ItemViewHolder
import woowacourse.movie.ui.viewHolder.MovieViewHolder

class MovieListAdapter(
    movie: List<MovieItemModel>,
    adv: List<AdvItemModel>
) : RecyclerView.Adapter<ItemViewHolder>() {

    private val _items: List<ItemModel>
    val items: List<ItemModel>
        get() = _items.toList()

    init {
        _items = if (adv.isEmpty()) {
            movie.toList()
        } else {
            var curAdvIndex = 0
            val advSize = adv.size
            val allowAdvMaxCount: Int = movie.size / 3
            mutableListOf<ItemModel>().apply {
                addAll(movie.toList())
                for (index in 3..(movie.size + allowAdvMaxCount) step 4) {
                    add(index, adv[(curAdvIndex++) % advSize])
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return when (ViewType.of(viewType)) {
            ViewType.MOVIE -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_item_layout, parent, false)
                MovieViewHolder(itemView)
            }
            ViewType.ADV -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adv_item_layout, parent, false)
                AdvViewHolder(itemView)
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
        return _items[position].viewType.id
    }
}
