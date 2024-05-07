package woowacourse.movie.home.view.adapter.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.home.view.adapter.movie.HomeContent.Movie
import woowacourse.movie.home.view.listener.MovieHomeClickListener

class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Movie,
        clickListener: MovieHomeClickListener,
    ) {
        binding.movie = movie
        binding.movieClickListener = clickListener
    }
}
