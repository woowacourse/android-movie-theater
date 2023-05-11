package woowacourse.movie.presentation.ui.main.fragments.home.recyclerview

import android.view.ViewGroup
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.ui.main.fragments.home.type.MovieViewType

class MovieListAdapter(
    onItemClick: (ListItem) -> Unit = {},
) : BaseRecyclerView.Adapter<ListItem>(onItemClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerView.BaseViewHolder = when (MovieViewType.get(viewType)) {
        MovieViewType.MOVIE -> {
            val movieBinding = ItemMovieBinding.inflate(parent.layoutInflater(), parent, false)
            MovieViewHolder(movieBinding, onItemViewClick)
        }

        MovieViewType.AD -> {
            val adView = parent.inflate(R.layout.item_native_ad)
            NativeAdViewHolder(adView, onItemViewClick)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is Movie -> MovieViewType.MOVIE.type
        is Ad -> MovieViewType.AD.type
        else -> throw IllegalArgumentException(INVALID_ITEM_TYPE)
    }

    fun appendAll(newItems: List<ListItem>) {
        val positionStart = items.size - 1
        items.addAll(newItems)
        notifyItemRangeChanged(positionStart, newItems.size)
    }

    companion object {
        private const val INVALID_ITEM_TYPE = "Invalid type"
    }
}
