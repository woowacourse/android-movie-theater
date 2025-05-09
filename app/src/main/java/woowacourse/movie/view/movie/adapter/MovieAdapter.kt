package woowacourse.movie.view.movie.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.view.movie.MovieClickListener

class MovieAdapter(
    private val clickListener: MovieClickListener,
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MoviesDiffUtil) {
    override fun getItemCount(): Int {
        val movieCount = super.getItemCount()
        val adCount = movieCount / MOVIE_COUNT
        return movieCount + adCount
    }

    override fun getItemViewType(position: Int): Int =
        if ((position + 1) % AD_INTERVAL == 0) {
            VIEW_TYPE_AD
        } else {
            VIEW_TYPE_MOVIE
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_MOVIE -> MovieViewHolder.from(parent, clickListener)
            VIEW_TYPE_AD -> AdViewHolder.from(parent)
            else -> throw IllegalArgumentException(ERROR_INVALID_VIEWTYPE)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieViewHolder -> {
                val moviePosition = getMoviePosition(position)
                holder.bind(getItem(moviePosition))
            }
            is AdViewHolder -> holder.bind()
        }
    }

    private fun getMoviePosition(adapterPosition: Int): Int = adapterPosition - (adapterPosition / AD_INTERVAL)

    companion object {
        private const val VIEW_TYPE_MOVIE = 0
        private const val VIEW_TYPE_AD = 1
        private const val MOVIE_COUNT = 3
        private const val AD_INTERVAL = 4
        private const val ERROR_INVALID_VIEWTYPE = "지원하지 않는 viewType입니다."
    }
}
