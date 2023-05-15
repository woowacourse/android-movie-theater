package woowacourse.movie.view.activities.home.fragments.reservationlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationListBinding
import java.time.format.DateTimeFormatter

class ReservationItemViewHolder(
    private val binding: ItemReservationListBinding,
    private val onClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(reservationListViewItemUIState: ReservationListViewItemUIState) {
        binding.tvScreeningDateTime.text =
            DATE_TIME_FORMATTER.format(reservationListViewItemUIState.screeningDateTime)
        binding.tvMovieTitle.text = reservationListViewItemUIState.movieTitle
        binding.root.setOnClickListener { onClick(reservationListViewItemUIState.reservationId) }
    }

    companion object {
        private val DATE_TIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    }
}
