package woowacourse.movie.view.moviemain.reservationlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ReservationItemBinding
import woowacourse.movie.util.DATE_FORMATTER
import woowacourse.movie.util.TIME_FORMATTER
import woowacourse.movie.view.model.ReservationUiModel

class ReservationItemViewHolder(
    private val binding: ReservationItemBinding,
    private val onItemClick: ReservationListAdapter.OnItemClick
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(reservation: ReservationUiModel) {
        val context = binding.root.context
        with(binding) {
            reservationDatetime.text = context.getString(
                R.string.datetime_with_line,
                reservation.screeningDateTime.format(DATE_FORMATTER),
                reservation.screeningDateTime.format(TIME_FORMATTER),
            )
            movieTitle.text = reservation.title
        }
        binding.reservationLayout.setOnClickListener {
            onItemClick.onClick(reservation)
        }
    }
}
