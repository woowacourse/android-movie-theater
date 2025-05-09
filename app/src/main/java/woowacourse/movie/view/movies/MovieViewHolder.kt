package woowacourse.movie.view.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.Movie

class MovieViewHolder(
    val parent: ViewGroup,
    private val eventListener: OnMovieEventListener,
    private val binding: ItemMovieBinding = inflate(parent),
) : MovieListViewHolder(binding.root) {
    init {
        binding.eventListener = eventListener
    }

    fun bind(movie: Movie) {
        binding.movie = movie
        binding.executePendingBindings()
    }

    companion object {
        fun inflate(parent: ViewGroup): ItemMovieBinding {
            return ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        }
    }
}
