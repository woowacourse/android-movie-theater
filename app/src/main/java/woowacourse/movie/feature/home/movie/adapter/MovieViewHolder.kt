package woowacourse.movie.feature.home.movie.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.movie.dto.Movie
import woowacourse.movie.databinding.ItemMovieBinding

class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Movie,
        onReservationButtonClick: ReservationButtonClickListener,
    ) {
        binding.movie = movie
        binding.btnReservation.setOnClickListener {
            onReservationButtonClick(movie.id)
        }
    }
}
