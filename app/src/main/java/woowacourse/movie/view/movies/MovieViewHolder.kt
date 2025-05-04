package woowacourse.movie.view.movies

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.Movie

class MovieViewHolder(
    val binding: ItemMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Movie,
        eventListener: OnMovieEventListener,
    ) {
        binding.movie = movie
        binding.eventListener = eventListener
        binding.executePendingBindings()
    }
}
