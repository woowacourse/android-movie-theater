package woowacourse.movie.view.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.MovieListItem

class MovieListAdapter(
    private val items: List<MovieListItem>,
    private val eventListener: OnMovieEventListener,
) : RecyclerView.Adapter<MovieListViewHolder>() {
    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is MovieListItem.AdItem -> ViewType.ITEM_AD.ordinal
            is MovieListItem.MovieItem -> ViewType.ITEM_MOVIE.ordinal
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieListViewHolder {
        return when (ViewType.find(viewType)) {
            ViewType.ITEM_MOVIE -> MovieViewHolder(parent, eventListener)
            ViewType.ITEM_AD -> AdViewHolder(parent)
        }
    }

    override fun getItemCount(): Int = if (items.size >= MAX_SIZE) MAX_SIZE else items.size

    override fun onBindViewHolder(
        holder: MovieListViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    companion object {
        private const val MAX_SIZE = 10_000
    }
}
