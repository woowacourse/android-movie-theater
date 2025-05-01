package woowacourse.movie.presentation.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.movie.Movie

class MovieAdapter(
    private val onClickMovie: (Movie) -> Unit,
) : ListAdapter<MovieListItem, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_movie -> {
                val binding =
                    DataBindingUtil.inflate<ItemMovieBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.item_movie,
                        parent,
                        false,
                    )
                MovieViewHolder(binding, onClickMovie)
            }

            R.layout.item_advertisement -> {
                val binding =
                    DataBindingUtil.inflate<ItemAdvertisementBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.item_advertisement,
                        parent,
                        false,
                    )
                AdvertisementViewHolder(binding)
            }

            else -> throw IllegalArgumentException(TYPE_ERROR)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieViewHolder -> holder.bind((getItem(position) as MovieListItem.MovieItem).movie)
            is AdvertisementViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is MovieListItem.MovieItem -> R.layout.item_movie
            is MovieListItem.AdvertisementItem -> R.layout.item_advertisement
        }

    companion object {
        private const val TYPE_ERROR = "[ERROR] 알 수 없는 타입입니다."

        private val diffCallback =
            object : DiffUtil.ItemCallback<MovieListItem>() {
                override fun areContentsTheSame(
                    oldItem: MovieListItem,
                    newItem: MovieListItem,
                ): Boolean = oldItem == newItem

                override fun areItemsTheSame(
                    oldItem: MovieListItem,
                    newItem: MovieListItem,
                ): Boolean =
                    when {
                        oldItem is MovieListItem.MovieItem && newItem is MovieListItem.MovieItem -> oldItem.movie == newItem.movie
                        oldItem is MovieListItem.AdvertisementItem && newItem is MovieListItem.AdvertisementItem -> oldItem.id == newItem.id
                        else -> false
                    }
            }
    }
}
