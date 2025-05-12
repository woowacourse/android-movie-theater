package woowacourse.movie.view.reservationDetails

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationDetailBinding
import woowacourse.movie.model.ticket.MovieTicket

class ReservationDetailViewHolder(
    private val binding: ItemReservationDetailBinding,
) : RecyclerView.ViewHolder(binding.root) {
    val button = binding.layoutReservationDetail

    fun bind(
        item: MovieTicket,
        reservationDetailClickListener: ReservationDetailClickListener,
    ) {
        binding.movieTicket = item
        binding.reservationDetailClickListener = reservationDetailClickListener
    }
}
