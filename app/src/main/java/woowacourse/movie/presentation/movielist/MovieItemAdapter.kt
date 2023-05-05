package woowacourse.movie.presentation.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieListAdItemBinding
import woowacourse.movie.databinding.MovieListItemBinding
import woowacourse.movie.presentation.movielist.viewholder.AdViewHolder
import woowacourse.movie.presentation.movielist.viewholder.MovieViewHolder

class MovieItemAdapter(
    private val movieItems: List<MovieItem>,
    private val clickBook: (Long) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var movieItemBinding: MovieListItemBinding
    private lateinit var adItemBinding: MovieListAdItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        movieItemBinding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context))
        adItemBinding = MovieListAdItemBinding.inflate(LayoutInflater.from(parent.context))

        return when (viewType) {
            R.layout.movie_list_item -> MovieViewHolder(movieItemBinding, clickBook)
            R.layout.movie_list_ad_item -> AdViewHolder(adItemBinding)
            else -> throw NoSuchElementException()
        }
    }

    override fun getItemCount(): Int = movieItems.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = movieItems[position]
        when (viewHolder) {
            is MovieViewHolder -> viewHolder.bind(item as MovieItem.Movie)
            is AdViewHolder -> viewHolder.bind(item as MovieItem.Ad)
        }
    }

    override fun getItemViewType(position: Int) = when (movieItems[position]) {
        is MovieItem.Movie -> R.layout.movie_list_item
        is MovieItem.Ad -> R.layout.movie_list_ad_item
    }
}
