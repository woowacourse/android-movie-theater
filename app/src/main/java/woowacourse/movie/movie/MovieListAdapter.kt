package woowacourse.movie.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.Movie

class MovieListAdapter(
    private val value: List<Movie>,
    private val movieClickListener: MovieClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MOVIE -> {
                val binding =
                    MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieViewHolder(binding)
            }

            else -> {
                val binding =
                    AdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isAdPosition(position) -> VIEW_TYPE_AD
            else -> VIEW_TYPE_MOVIE
        }
    }

    private fun isAdPosition(position: Int): Boolean {
        return (position + 1) % 4 == 0
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if (getItemViewType(position) == VIEW_TYPE_MOVIE) {
            val realPosition = position - (position / 4)
            (holder as MovieViewHolder).bindMovie(value[realPosition], movieClickListener)
        } else {
            (holder as AdViewHolder).bindAd(movieClickListener)
        }
    }

    override fun getItemCount(): Int {
        val movieCount = value.size
        val adCount = movieCount / 3
        return movieCount + adCount
    }

    companion object {
        private const val VIEW_TYPE_MOVIE = 0
        private const val VIEW_TYPE_AD = 1
    }
}
