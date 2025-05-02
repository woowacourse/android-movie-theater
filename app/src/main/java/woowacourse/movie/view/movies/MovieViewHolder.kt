package woowacourse.movie.view.movies

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.Movie

class MovieViewHolder(
//    val view: View,
    val binding: ItemMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Movie,
        eventListener: OnMovieEventListener,
    ) {
        binding.movie = movie
        binding.ivPoster.setImageResource(movie.poster.toInt())
        binding.btnReservation.setOnClickListener {
            eventListener.onReserveButtonClick(movie)
        }
        binding.executePendingBindings()
    }
}
