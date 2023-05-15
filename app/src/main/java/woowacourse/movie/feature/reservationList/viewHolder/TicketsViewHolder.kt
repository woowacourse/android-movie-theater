package woowacourse.movie.feature.reservationList.viewHolder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationLayoutBinding
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.util.DateTimeFormatters

class TicketsViewHolder(private val binding: ItemReservationLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(itemModel: TicketsItemModel) {
        binding.tickets = itemModel
        binding.screeningDateTimeTextView.text =
            binding.root.context.getString(R.string.date_time_form)
                .format(
                    DateTimeFormatters.convertToDate(itemModel.ticketsState.dateTime.toLocalDate()),
                    itemModel.ticketsState.dateTime.toLocalTime().toString()
                )
    }
}
