package woowacourse.movie.view.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding

class MovieListAdapter(
    private val items: List<MovieListItem>,
    private val eventListener: OnMovieEventListener,
) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is MovieListItem.AdItem -> ViewType.ITEM_AD.ordinal
            is MovieListItem.MovieItem -> ViewType.ITEM_MOVIE.ordinal
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (ViewType.find(viewType)) {
            ViewType.ITEM_MOVIE -> {
                val binding =
                    ItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                MovieViewHolder(binding, eventListener)
            }

            ViewType.ITEM_AD -> {
                val binding =
                    ItemAdvertisementBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                AdViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = if (items.size >= MAX_SIZE) MAX_SIZE else items.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (val item = items[position]) {
            is MovieListItem.AdItem -> (holder as AdViewHolder).bind(item.ad)
            is MovieListItem.MovieItem ->
                (holder as MovieViewHolder).bind(
                    item.movie,
                )
        }
    }

    companion object {
        private const val MAX_SIZE = 10_000
    }
}
