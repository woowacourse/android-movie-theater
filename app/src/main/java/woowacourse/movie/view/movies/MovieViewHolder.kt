package woowacourse.movie.view.movies

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.Movie

class MovieViewHolder(
    val binding: ItemMovieBinding,
    private val eventListener: OnMovieEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var item: Movie

    init {
        binding.eventListener = eventListener
    }

    fun bind(movie: Movie) {
        item = movie
        binding.movie = movie
        binding.executePendingBindings()
    }
}
