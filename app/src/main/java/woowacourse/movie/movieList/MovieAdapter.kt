package woowacourse.movie.movieList

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.databinding.ItemMovieListBinding
import woowacourse.movie.model.ui.Ad
import woowacourse.movie.model.ui.Movie
import woowacourse.movie.model.ui.MovieItem

class MovieAdapter(
    private val context: Context,
    private val onClick: (position: Int) -> Unit,
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var items: List<MovieItem> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            Movie.ITEM_VIEW_TYPE_MOVIE -> {
                val view = ItemMovieListBinding.inflate(layoutInflater, parent, false)
                MovieItemViewHolder(view, onClick)
            }

            Ad.ITEM_VIEW_TYPE_AD -> {
                val view = ItemAdBinding.inflate(layoutInflater, parent, false)
                AdItemViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieItemViewHolder -> holder.bind(items[position] as Movie)
            is AdItemViewHolder -> holder.bind(items[position] as Ad)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(displayData: List<MovieItem>) {
        items = displayData
        notifyDataSetChanged()
    }

    sealed class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class MovieItemViewHolder(
        private val binding: ItemMovieListBinding,
        private val onClick: (position: Int) -> Unit,
    ) :
        MovieViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.data = movie
            binding.listener = MovieItemListener { onClick(absoluteAdapterPosition) }
        }
    }

    class AdItemViewHolder(private val binding: ItemAdBinding) : MovieViewHolder(binding.root) {
        fun bind(ad: Ad) {
            binding.data = ad
        }
    }
}
