package woowacourse.movie.presentation.movies.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.common.adapter.ClickListener
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.movie.Movie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onClickMovie: (Movie) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        binding.movie = item
        binding.handler = ClickListener<Movie> { onClickMovie(it) }
        binding.executePendingBindings()
    }
}
