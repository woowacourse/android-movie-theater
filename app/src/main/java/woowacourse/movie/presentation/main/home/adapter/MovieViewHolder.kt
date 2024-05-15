package woowacourse.movie.presentation.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.model.home.HomeItem

class MovieViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: HomeItem.MovieItem,
        onMovieItemClickListener: OnMovieItemClickListener,
    ) {
        binding.movie = movie
        binding.onMovieItemClickListener = onMovieItemClickListener
    }
}
