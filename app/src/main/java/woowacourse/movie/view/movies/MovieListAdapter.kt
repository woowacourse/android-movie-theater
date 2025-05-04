package woowacourse.movie.view.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding

class MovieListAdapter(
    private val items: List<MovieListItem>,
    private val eventListener: OnMovieEventListener,
) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is MovieListItem.AdItem -> R.layout.item_advertisement
            is MovieListItem.MovieItem -> R.layout.item_movie
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            R.layout.item_movie -> {
                val binding =
                    ItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                MovieViewHolder(binding)
            }

            R.layout.item_advertisement -> {
                val binding =
                    ItemAdvertisementBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                AdViewHolder(binding)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (val item = items[position]) {
            is MovieListItem.AdItem -> (holder as AdViewHolder).bind(item.ad)
            is MovieListItem.MovieItem ->
                (holder as MovieViewHolder).bind(
                    item.movie,
                    eventListener,
                )
        }
    }
}
