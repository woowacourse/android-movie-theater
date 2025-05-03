package woowacourse.movie.presentation.movies.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.common.adapter.ItemClickListener
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.movie.Movie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    clickListener: ClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: Movie

    init {
        binding.handler = ItemClickListener<Movie> { clickListener.onClickMovie(currentItem) }
    }

    fun bind(item: MovieListItem.MovieItem) {
        val movie = item.movie
        currentItem = movie
        binding.movie = movie
        binding.executePendingBindings()
    }

    interface ClickListener {
        fun onClickMovie(item: Movie)
    }
}
