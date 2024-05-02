package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieContentBinding
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.ui.ReservationButtonClickListener

class MovieViewHolder(
    private val binding: ItemMovieContentBinding,
    private val reservationButtonClickListener: ReservationButtonClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movieContent: MovieContent) {
        binding.movieContent = movieContent
        binding.reservationButton.setOnClickListener {
            reservationButtonClickListener.onClick(it, movieContent.id)
        }
    }
}
