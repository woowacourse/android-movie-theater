package woowacourse.movie.ui.movielist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.model.item.MovieListItem
import woowacourse.movie.domain.model.item.MovieListItem.AdItem
import woowacourse.movie.domain.model.item.MovieListItem.MovieItem
import woowacourse.movie.domain.model.movie.Movie

class MovieAdapter(
    private val onClickBooking: (Movie) -> Unit,
) : ListAdapter<MovieListItem, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<MovieListItem>() {
            override fun areItemsTheSame(
                oldItem: MovieListItem,
                newItem: MovieListItem,
            ): Boolean =
                when {
                    oldItem is MovieItem && newItem is MovieItem -> oldItem.movie.title == newItem.movie.title
                    oldItem is AdItem && newItem is AdItem -> oldItem.advertisement.image == newItem.advertisement.image
                    else -> false
                }

            override fun areContentsTheSame(
                oldItem: MovieListItem,
                newItem: MovieListItem,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is MovieItem -> VIEW_TYPE_MOVIE
            is AdItem -> VIEW_TYPE_ADVERTISEMENT
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == VIEW_TYPE_MOVIE) {
            val movieItemBinding = MovieItemBinding.inflate(inflater, parent, false)
            return MovieViewHolder(movieItemBinding, onClickBooking)
        }
        val advertisementBinding = AdvertisementItemBinding.inflate(inflater, parent, false)
        return AdvertisementViewHolder(advertisementBinding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is MovieItem -> (holder as MovieViewHolder).bind(item.movie)
            is AdItem -> (holder as AdvertisementViewHolder).bind(item.advertisement)
        }
    }

    companion object {
        private const val VIEW_TYPE_MOVIE = 1
        private const val VIEW_TYPE_ADVERTISEMENT = 2
    }
}
