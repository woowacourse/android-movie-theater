package woowacourse.movie.presentation.main.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieTicketItemBinding
import woowacourse.movie.domain.model.movieticket.MovieTicket
import woowacourse.movie.presentation.main.history.ReservationHistoryContract

class MovieTicketViewHolder(private val binding: MovieTicketItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movieTicket: MovieTicket,
        clickListener: ReservationHistoryContract.Presenter,
    ) {
        binding.movieTicket = movieTicket
        binding.onMovieTicketItemClickListener = clickListener
    }
}
