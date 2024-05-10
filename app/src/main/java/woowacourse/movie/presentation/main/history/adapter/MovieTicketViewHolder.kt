package woowacourse.movie.presentation.main.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieTicketItemBinding
import woowacourse.movie.domain.model.movieticket.MovieTicket

class MovieTicketViewHolder(private val binding: MovieTicketItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movieTicket: MovieTicket,
        onMovieTicketItemClickListener: OnMovieTicketItemClickListener,
    ) {
        binding.movieTicket = movieTicket
        binding.onMovieTicketItemClickListener = onMovieTicketItemClickListener
    }
}
