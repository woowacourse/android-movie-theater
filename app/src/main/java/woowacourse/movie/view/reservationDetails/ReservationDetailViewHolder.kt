package woowacourse.movie.view.reservationDetails

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationDetailBinding
import woowacourse.movie.model.ticket.MovieTicket

class ReservationDetailViewHolder(
    private val binding: ItemReservationDetailBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MovieTicket) {
        binding.movieTicket = item
    }
}
