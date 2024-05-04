package woowacourse.movie.feature.home.movie.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.feature.home.movie.listener.ReservationButtonClickListener
import woowacourse.movie.model.Movie

class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Movie,
        onReservationButtonClick: ReservationButtonClickListener,
    ) {
        binding.movie = movie
        binding.reservationButtonClickListener = onReservationButtonClick
    }
}
