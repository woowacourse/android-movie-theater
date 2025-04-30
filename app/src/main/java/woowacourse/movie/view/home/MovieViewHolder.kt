package woowacourse.movie.view.home

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.movie.Movie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {
    val button = binding.btnMovieReservation

    fun bind(item: Movie) {
        binding.movie = item
    }
}
