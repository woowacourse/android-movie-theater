package woowacourse.movie.presentation.homefragments.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.homefragments.movieList.listener.MovieListClickListener
import woowacourse.movie.presentation.homefragments.movieList.viewholder.AdViewHolder
import woowacourse.movie.presentation.homefragments.movieList.viewholder.MovieViewHolder

class MovieAdapter(
    private val listener: MovieListClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MOVIE_VIEW_TYPE -> {
                val binding =
                    ItemMovieBinding.inflate(inflater, parent, false)
                MovieViewHolder(binding, listener)
            }

            else -> {
                val binding =
                    ItemAdBinding.inflate(inflater, parent, false)
                AdViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = movies[(position - (position + 1) / AD_POSITION)]
        when (holder) {
            is MovieViewHolder -> holder.bind(item)
            is AdViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if ((position + 1) % AD_POSITION == 0) return AD_VIEW_TYPE
        return MOVIE_VIEW_TYPE
    }

    override fun getItemId(position: Int): Long = movies[position].id

    override fun getItemCount(): Int {
        val adCount = if (movies.size >= AD_INTERVAL) movies.size / AD_INTERVAL else 0
        return movies.size + adCount
    }

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    companion object {
        private const val MOVIE_VIEW_TYPE = 0
        private const val AD_VIEW_TYPE = 1
        private const val AD_INTERVAL = 3
        private const val AD_POSITION = 4
    }
}
