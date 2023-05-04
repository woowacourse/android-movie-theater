package woowacourse.movie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvLayoutBinding
import woowacourse.movie.databinding.ItemMovieLayoutBinding
import woowacourse.movie.ui.itemModel.AdvItemModel
import woowacourse.movie.ui.itemModel.ItemModel
import woowacourse.movie.ui.itemModel.MovieItemModel
import woowacourse.movie.ui.viewHolder.AdvViewHolder
import woowacourse.movie.ui.viewHolder.ItemViewHolder
import woowacourse.movie.ui.viewHolder.MovieViewHolder

class MovieListAdapter(
    movie: List<MovieItemModel>,
    adv: List<AdvItemModel>,
    private val onClickMovie: (MovieItemModel) -> Unit,
    private val onClickAdv: (AdvItemModel) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {

    private val items: List<ItemModel>

    init {
        items = if (adv.isEmpty()) {
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
    ): ItemViewHolder = when (viewType) {
        MovieItemModel.type -> {
            val binding = ItemMovieLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MovieViewHolder(binding) { position ->
                onClickMovie(items[position] as MovieItemModel)
            }
        }
        AdvItemModel.type -> {
            val binding = ItemAdvLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            AdvViewHolder(binding) { position ->
                onClickAdv(items[position] as AdvItemModel)
            }
        }
        else -> throw IllegalArgumentException(ERROR_INVALID_TYPE)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is MovieItemModel -> MovieItemModel.type
        is AdvItemModel -> AdvItemModel.type
        else -> throw IllegalArgumentException(ERROR_INVALID_TYPE)
    }

    companion object {
        const val ERROR_INVALID_TYPE = "Invalid type"
    }
}
