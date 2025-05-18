package woowacourse.movie.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.AdItemBinding
import woowacourse.movie.databinding.MovieItemBinding

class MovieListAdapter(
    private val items: List<FeedItem>,
    private val movieClickListener: MovieClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.movie_item -> {
                val binding =
                    MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieViewHolder(binding, movieClickListener)
            }

            R.layout.ad_item -> {
                val binding =
                    AdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdViewHolder(binding)
            }

            else -> throw IllegalArgumentException("지원 하지 않는 타입입니다.")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FeedItem.MovieItem -> R.layout.movie_item
            is FeedItem.AdvertiseItem -> R.layout.ad_item
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (items[position]) {
            is FeedItem.MovieItem -> {
                (holder as MovieViewHolder).bindMovie(items[position] as FeedItem.MovieItem)
            }
            is FeedItem.AdvertiseItem -> {
                (holder as AdViewHolder).bindAd(movieClickListener)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
