package woowacourse.movie.presentation.view.home.movies.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.presentation.model.MovieUiModel

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val eventListener: OnMovieEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieUiModel) {
        binding.itemMovie = movie
        binding.btnReservation.setOnClickListener {
            eventListener.onClick(movie)
        }
        binding.executePendingBindings()
    }
}
