package woowacourse.movie.presentation.view.navigation

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationItemBinding
import woowacourse.movie.domain.model.reservation.ReservationMovieInfo

class ReservationViewHolder(
    private val binding: ReservationItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ticket: ReservationMovieInfo) {
        binding.reservation = ticket
    }
}
