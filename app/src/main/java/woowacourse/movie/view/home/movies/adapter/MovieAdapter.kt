package woowacourse.movie.view.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.AdType
import woowacourse.movie.domain.ItemType
import woowacourse.movie.domain.MovieListItem
import woowacourse.movie.view.home.movies.OnMovieEventListener

class MovieAdapter(
    private val eventListener: OnMovieEventListener,
) : ListAdapter<MovieListItem, RecyclerView.ViewHolder>(MovieListItemDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ItemType.MOVIE.viewType -> {
                val binding = MovieItemBinding.inflate(inflater, parent, false)
                MovieViewHolder(eventListener, binding)
            }

            ItemType.AD_BANNER.viewType -> {
                val binding = AdvertisementItemBinding.inflate(inflater, parent, false)
                AdViewHolder(binding)
            }

            else -> throw IllegalArgumentException("존재하지 않는 뷰타입입니다.")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is MovieListItem.ItemMovie -> {
                if (holder is MovieViewHolder) {
                    holder.bind(item.movie)
                }
            }

            is MovieListItem.ItemAd -> {
                when (val ad = item.ad) {
                    is AdType.Banner -> (holder as? AdViewHolder)?.bind(ad.imageUrl)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    companion object {
        private val MovieListItemDiffCallback =
            object : DiffUtil.ItemCallback<MovieListItem>() {
                override fun areItemsTheSame(
                    oldItem: MovieListItem,
                    newItem: MovieListItem,
                ): Boolean {
                    return when {
                        oldItem is MovieListItem.ItemMovie && newItem is MovieListItem.ItemMovie ->
                            oldItem.movie.title == newItem.movie.title

                        oldItem is MovieListItem.ItemAd && newItem is MovieListItem.ItemAd ->
                            true

                        else -> false
                    }
                }

                override fun areContentsTheSame(
                    oldItem: MovieListItem,
                    newItem: MovieListItem,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
