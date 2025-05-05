package woowacourse.movie.view.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.MovieItem
import woowacourse.movie.view.home.movies.OnMovieEventListener

class MovieAdapter(
    private val eventListener: OnMovieEventListener,
) : ListAdapter<MovieItem, RecyclerView.ViewHolder>(MovieItemDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            MovieItem.TYPE_MOVIE -> {
                val binding = MovieItemBinding.inflate(inflater, parent, false)
                MovieViewHolder(eventListener, binding)
            }

            MovieItem.TYPE_AD -> {
                val view = inflater.inflate(R.layout.advertisement_item, parent, false)
                AdViewHolder(view)
            }

            else -> throw IllegalArgumentException("존재하지 않는 뷰타입입니다.")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is MovieItem.ItemMovie -> {
                if (holder is MovieViewHolder) {
                    holder.bind(item.movie)
                }
            }

            is MovieItem.ItemAd -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    companion object {
        private val MovieItemDiffCallback =
            object : DiffUtil.ItemCallback<MovieItem>() {
                override fun areItemsTheSame(
                    oldItem: MovieItem,
                    newItem: MovieItem,
                ): Boolean {
                    return when {
                        oldItem is MovieItem.ItemMovie && newItem is MovieItem.ItemMovie ->
                            oldItem.movie.title == newItem.movie.title

                        oldItem is MovieItem.ItemAd && newItem is MovieItem.ItemAd ->
                            true

                        else -> false
                    }
                }

                override fun areContentsTheSame(
                    oldItem: MovieItem,
                    newItem: MovieItem,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
