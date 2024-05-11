package woowacourse.movie.feature.history.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.movie.MovieRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.feature.history.ui.ReservationHistoryUiModel

class ReservationHistoryViewHolder(
    private val binding: ItemReservationHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        ticket: Ticket,
        onReservationHistoryItemClickListener: ReservationHistoryItemClickListener,
    ) {
        val movie = MovieRepository.getMovieById(ticket.movieId)
        binding.reservationHistory = ReservationHistoryUiModel.from(movie, ticket)
        binding.root.setOnClickListener {
            onReservationHistoryItemClickListener(ticket)
        }
    }
}
