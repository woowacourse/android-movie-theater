package woowacourse.movie.feature.reservation.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.feature.DateTimeFormatters
import woowacourse.movie.model.TicketsState

class TicketsViewHolder(
    binding: ViewDataBinding,
    private val itemClickEvent: (ticketsState: TicketsState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    val binding = binding as ItemReservationBinding

    fun bind(ticketsState: TicketsState) {
        val context = binding.screeningDateTimeTextView.context

        binding.screeningDateTimeTextView.text = context.getString(R.string.date_time_form)
            .format(
                DateTimeFormatters.convertToDate(ticketsState.dateTime.toLocalDate()),
                ticketsState.dateTime.toLocalTime().toString()
            )
        binding.movieNameTextView.text = ticketsState.movie.title

        binding.reservationItemBox.setOnClickListener { itemClickEvent(ticketsState) }
    }
}
