package woowacourse.movie.view.movies

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.Movie

class MovieViewHolder(
    val binding: ItemMovieBinding,
    private val eventListener: OnMovieEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.eventListener = eventListener
    }

    fun bind(movie: Movie) {
        binding.movie = movie
        binding.executePendingBindings()
    }
}
